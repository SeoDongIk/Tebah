package com.example.data.source.remote

import com.example.data.model.dto.UserDto

interface UserRemoteDataSource {
    suspend fun getUserById(userId: String): Result<UserDto>
    suspend fun getUsersByChurch(churchId: String): Result<List<UserDto>>
    suspend fun followUser(fromUserId: String, toUserId: String): Result<Unit>
    suspend fun getFollowees(userId: String): Result<List<UserDto>>
    suspend fun getFollowers(userId: String): Result<List<UserDto>>
}