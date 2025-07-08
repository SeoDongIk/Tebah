package com.example.data.repository

import com.example.domain.model.Channel
import com.example.domain.model.CreateChannelRequest
import com.example.domain.model.RootChannel
import com.example.domain.repository.ChannelRepository
import javax.inject.Inject

class ChannelRepositoryImpl @Inject constructor(

) :ChannelRepository{
    override suspend fun approveOfficialChannel(channelId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun createChannel(request: CreateChannelRequest): Result<Channel> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteChannel(channelId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRootChannels(): Result<List<RootChannel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getChannelsByRootChannel(rootChannelId: String): Result<List<Channel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getChannelsBySearchTerm(query: String): Result<List<Channel>> {
        TODO("Not yet implemented")
    }

    override suspend fun subscribeChannel(channelId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun unsubscribeChannel(channelId: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}