package com.example.domain.repository

import com.example.domain.model.User

interface UserRepository {
    suspend fun getUserById(userId: String): Result<User>
    suspend fun getUsersByChurch(churchId: String): Result<List<User>>
    suspend fun followUser(fromUserId: String, toUserId: String): Result<Unit>
    suspend fun getFollowees(userId: String): Result<List<User>>
    suspend fun getFollowers(userId: String): Result<List<User>>
}