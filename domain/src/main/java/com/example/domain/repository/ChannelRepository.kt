package com.example.domain.repository

import com.example.domain.model.Channel
import com.example.domain.model.CreateChannelRequest
import com.example.domain.model.RootChannel

interface ChannelRepository {
    suspend fun approveOfficialChannel(channelId: String): Result<Unit>
    suspend fun createChannel(request: CreateChannelRequest): Result<Channel>
    suspend fun deleteChannel(channelId: String): Result<Unit>
    suspend fun getAllRootChannels(): Result<List<RootChannel>>
    suspend fun getChannelsByRootChannel(rootChannelId: String): Result<List<Channel>>
    suspend fun getChannelsBySearchTerm(query: String): Result<List<Channel>>
    suspend fun subscribeChannel(channelId: String): Result<Unit>
    suspend fun unsubscribeChannel(channelId: String): Result<Unit>
}