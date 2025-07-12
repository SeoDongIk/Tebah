package com.example.data.source.remote.impl

import com.example.data.model.dto.UserDto
import com.example.data.source.remote.UserRemoteDataSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserRemoteDataSource {

    private val usersCollection = firestore.collection("users")
    private val followsCollection = firestore.collection("follows")

    override suspend fun getUserById(userId: String): Result<UserDto> = runCatching {
        val snapshot = usersCollection.document(userId).get().await()
        snapshot.toObject(UserDto::class.java)
            ?: throw NoSuchElementException("No user found with id: $userId")
    }

    override suspend fun getUsersByChurch(churchId: String): Result<List<UserDto>> = runCatching {
        usersCollection
            .whereEqualTo("churchId", churchId)
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(UserDto::class.java) }
    }

    override suspend fun followUser(fromUserId: String, toUserId: String): Result<Unit> = runCatching {
        val followId = "$fromUserId->$toUserId"
        val data = mapOf(
            "from" to fromUserId,
            "to" to toUserId,
            "timestamp" to System.currentTimeMillis()
        )
        followsCollection.document(followId).set(data).await()
    }

    override suspend fun getFollowees(userId: String): Result<List<UserDto>> = runCatching {
        val followees = followsCollection
            .whereEqualTo("from", userId)
            .get()
            .await()
            .documents
            .mapNotNull { it.getString("to") }

        if (followees.isEmpty()) return@runCatching emptyList()

        usersCollection
            .whereIn("id", followees.take(10)) // Firestore IN 쿼리는 max 10개
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(UserDto::class.java) }
    }

    override suspend fun getFollowers(userId: String): Result<List<UserDto>> = runCatching {
        val followers = followsCollection
            .whereEqualTo("to", userId)
            .get()
            .await()
            .documents
            .mapNotNull { it.getString("from") }

        if (followers.isEmpty()) return@runCatching emptyList()

        usersCollection
            .whereIn("id", followers.take(10)) // Firestore IN 쿼리는 max 10개
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(UserDto::class.java) }
    }
}