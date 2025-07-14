package com.example.data.repository

import com.example.data.mapper.toDomain
import com.example.data.source.remote.FollowRemoteDataSource
import com.example.domain.model.User
import com.example.domain.repository.FollowRepository

class FollowRepositoryImpl(
    private val remoteDataSource: FollowRemoteDataSource
) : FollowRepository {

    override suspend fun followUser(fromUserId: String, toUserId: String): Result<Unit> =
        remoteDataSource.followUser(fromUserId, toUserId)

    override suspend fun unfollowUser(fromUserId: String, toUserId: String): Result<Unit> =
        remoteDataSource.unfollowUser(fromUserId, toUserId)

    override suspend fun isFollowing(fromUserId: String, toUserId: String): Result<Boolean> =
        remoteDataSource.isFollowing(fromUserId, toUserId)

    override suspend fun getFollowers(userId: String): Result<List<User>> =
        remoteDataSource.getFollowers(userId).map { list -> list.map{ it.toDomain()} }

    override suspend fun getFollowees(userId: String): Result<List<User>> =
        remoteDataSource.getFollowees(userId).map { list -> list.map{ it.toDomain() } }
}