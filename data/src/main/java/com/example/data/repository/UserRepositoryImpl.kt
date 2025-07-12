package com.example.data.repository

import com.example.data.mapper.toDomain
import com.example.data.source.local.UserLocalDataSource
import com.example.data.source.remote.UserRemoteDataSource
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val local: UserLocalDataSource,
    private val remote: UserRemoteDataSource
): UserRepository {

    override suspend fun getUserById(userId: String): Result<User> {
        val entity = local.getUserById(userId)
        return entity?.let { Result.success(it.toDomain()) }
            ?: remote.getUserById(userId).map { it.toDomain() }
    }

    override suspend fun getUsersByChurch(churchId: String): Result<List<User>> {
        return remote.getUsersByChurch(churchId).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun followUser(fromUserId: String, toUserId: String): Result<Unit> {
        return remote.followUser(fromUserId, toUserId)
    }

    override suspend fun getFollowees(userId: String): Result<List<User>> {
        return remote.getFollowees(userId).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getFollowers(userId: String): Result<List<User>> {
        return remote.getFollowers(userId).map { list -> list.map { it.toDomain() } }
    }
}