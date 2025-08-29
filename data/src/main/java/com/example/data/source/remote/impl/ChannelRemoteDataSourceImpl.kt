package com.example.data.source.remote.impl

import com.example.data.model.dto.ChannelDto
import com.example.data.source.remote.ChannelRemoteDataSource
import com.example.domain.model.ChannelCreateRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChannelRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ChannelRemoteDataSource {

    override suspend fun createChannel(request: ChannelCreateRequest): Result<ChannelDto> = runCatching {
        val docRef = firestore.collection("channels").document()
        val dto = ChannelDto(
            id = docRef.id,
            name = request.name,
            description = request.description,
            isOfficial = false,
            createdAt = System.currentTimeMillis(),
            churchId = request.churchId,
            ownerId = request.ownerId
        )
        docRef.set(dto).await()
        dto
    }

    override suspend fun approveChannel(channelId: String): Result<Unit> = runCatching {
        firestore.collection("channels")
            .document(channelId)
            .update("isOfficial", true)
            .await()
    }

    override suspend fun getChannelsByChurchId(churchId: String): Result<List<ChannelDto>> = runCatching {
        firestore.collection("channels")
            .whereEqualTo("churchId", churchId)
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(ChannelDto::class.java) }
    }

    override suspend fun subscribeToChannel(userId: String, channelId: String): Result<Unit> = runCatching {
        val userSubscriptionRef = firestore.collection("users")
            .document(userId)
            .collection("channelSubscriptions")
            .document(channelId)

        val channelSubscriberRef = firestore.collection("channels")
            .document(channelId)
            .collection("subscribers")
            .document(userId)

        val data = mapOf("subscribedAt" to System.currentTimeMillis())

        firestore.runBatch { batch ->
            batch.set(userSubscriptionRef, data)
            batch.set(channelSubscriberRef, data)
        }.await()
    }

    override suspend fun getChannelSubscriptions(userId: String): Result<List<ChannelDto>> = runCatching {
        val snapshot = firestore.collection("users")
            .document(userId)
            .collection("channelSubscriptions")
            .get()
            .await()

        val channelIds = snapshot.documents.map { it.id }

        channelIds.mapNotNull { id ->
            firestore.collection("channels")
                .document(id)
                .get()
                .await()
                .toObject(ChannelDto::class.java)
        }
    }
}