package com.example.data.repository

import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntity
import com.example.data.source.local.ChannelLocalDataSource
import com.example.data.source.local.UserLocalDataSource
import com.example.data.source.remote.ChannelRemoteDataSource
import com.example.domain.model.Channel
import com.example.domain.model.ChannelCreateRequest
import com.example.domain.repository.ChannelRepository
import javax.inject.Inject

class ChannelRepositoryImpl @Inject constructor(
    private val remote: ChannelRemoteDataSource,
    private val local: ChannelLocalDataSource,
    private val userLocal: UserLocalDataSource
) : ChannelRepository {
    override suspend fun createChannel(request: ChannelCreateRequest): Result<Channel> {
        return remote.createChannel(request).map { dto -> dto.toDomain() }
    }

    override suspend fun approveChannel(channelId: String): Result<Unit> =
        remote.approveChannel(channelId)

    override suspend fun getChannelsByChurchId(churchId: String): Result<List<Channel>> {
        val result = remote.getChannelsByChurchId(churchId)
        result.getOrNull()?.let {
            local.saveChannels(it.map { dto -> dto.toEntity() })
        }
        return result.map { list -> list.map { it.toDomain() } }
    }

    override suspend fun subscribeToChannel(channelId: String): Result<Unit> {
        val user = userLocal.getAuthUser() ?: return Result.failure(Exception("User not logged in"))
        return remote.subscribeToChannel(user.id, channelId)
    }

    override suspend fun getMySubscribedChannels(): Result<List<Channel>> {
        val user = userLocal.getAuthUser() ?: return Result.failure(Exception("User not logged in"))
        return remote.getChannelSubscriptions(user.id).map { list -> list.map { it.toDomain() } }
    }
}