package com.example.data.source.remote

import com.example.data.model.dto.ChannelDto
import com.example.domain.model.ChannelCreateRequest

interface ChannelRemoteDataSource {
    suspend fun createChannel(request: ChannelCreateRequest): Result<ChannelDto>
    suspend fun approveChannel(channelId: String): Result<Unit>
    suspend fun getChannelsByChurchId(churchId: String): Result<List<ChannelDto>>
    suspend fun subscribeToChannel(userId: String, channelId: String): Result<Unit>
    suspend fun getChannelSubscriptions(userId: String): Result<List<ChannelDto>>
}