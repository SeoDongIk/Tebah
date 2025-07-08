package com.example.data.source.remote

import com.example.domain.model.Channel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteChannelDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : RemoteChannelDataSource {

//    override suspend fun createChannel(channel: Channel): Result<Unit> = runCatching {
//        firestore.collection("channels").document(channel.id).set(channel).await()
//    }
//
//    override suspend fun getChannels(): Result<List<Channel>> = runCatching {
//        firestore.collection("channels")
//            .get()
//            .await()
//            .documents.mapNotNull { it.toObject(Channel::class.java) }
//    }
}