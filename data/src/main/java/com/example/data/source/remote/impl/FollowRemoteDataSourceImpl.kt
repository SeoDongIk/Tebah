package com.example.data.source.remote.impl

import com.example.data.model.dto.UserDto
import com.example.data.source.remote.FollowRemoteDataSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FollowRemoteDataSourceImpl(
    private val firestore: FirebaseFirestore
) : FollowRemoteDataSource {

    override suspend fun followUser(fromUserId: String, toUserId: String): Result<Unit> = runCatching {
        val now = System.currentTimeMillis()

        val followeeRef = firestore.collection("users").document(fromUserId)
            .collection("followees").document(toUserId)
        val followerRef = firestore.collection("users").document(toUserId)
            .collection("followers").document(fromUserId)

        firestore.runBatch { batch ->
            batch.set(followeeRef, mapOf("followedAt" to now))
            batch.set(followerRef, mapOf("followedAt" to now))
        }.await()
    }

    override suspend fun unfollowUser(fromUserId: String, toUserId: String): Result<Unit> = runCatching {
        val followeeRef = firestore.collection("users").document(fromUserId)
            .collection("followees").document(toUserId)
        val followerRef = firestore.collection("users").document(toUserId)
            .collection("followers").document(fromUserId)

        firestore.runBatch { batch ->
            batch.delete(followeeRef)
            batch.delete(followerRef)
        }.await()
    }

    override suspend fun isFollowing(fromUserId: String, toUserId: String): Result<Boolean> = runCatching {
        val snapshot = firestore.collection("users").document(fromUserId)
            .collection("followees").document(toUserId)
            .get().await()
        snapshot.exists()
    }

    override suspend fun getFollowers(userId: String): Result<List<UserDto>> = runCatching {
        val followerSnapshot = firestore.collection("users").document(userId)
            .collection("followers").get().await()

        val followerIds = followerSnapshot.documents.map { it.id }

        followerIds.mapNotNull { id ->
            firestore.collection("users").document(id).get().await().toObject(UserDto::class.java)
        }
    }

    override suspend fun getFollowees(userId: String): Result<List<UserDto>> = runCatching {
        val followeeSnapshot = firestore.collection("users").document(userId)
            .collection("followees").get().await()

        val followeeIds = followeeSnapshot.documents.map { it.id }

        followeeIds.mapNotNull { id ->
            firestore.collection("users").document(id).get().await().toObject(UserDto::class.java)
        }
    }
}