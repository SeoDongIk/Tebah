package com.example.data.source.local

import com.example.data.model.entity.ChannelEntity

interface ChannelLocalDataSource {
    suspend fun saveChannels(channels: List<ChannelEntity>)
    suspend fun getChannelsByChurchId(churchId: String): List<ChannelEntity>
    suspend fun deleteChannelsByChurch(churchId: String)
}