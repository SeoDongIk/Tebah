package com.example.domain.repository

import com.example.domain.model.User

interface FollowRepository {
    suspend fun followUser(fromUserId: String, toUserId: String): Result<Unit>
    suspend fun unfollowUser(fromUserId: String, toUserId: String): Result<Unit>
    suspend fun isFollowing(fromUserId: String, toUserId: String): Result<Boolean>
    suspend fun getFollowers(userId: String): Result<List<User>>
    suspend fun getFollowees(userId: String): Result<List<User>>
}