package com.example.domain.repository

import com.example.domain.model.Channel
import com.example.domain.model.ChannelCreateRequest

interface ChannelRepository {
    suspend fun createChannel(request: ChannelCreateRequest): Result<Channel>
    suspend fun approveChannel(channelId: String): Result<Unit>
    suspend fun getChannelsByChurchId(churchId: String): Result<List<Channel>>
    suspend fun subscribeToChannel(channelId: String): Result<Unit>
    suspend fun getMySubscribedChannels(): Result<List<Channel>>
}