package com.example.data.source.local.impl

import com.example.data.model.entity.ChannelEntity
import com.example.data.source.local.ChannelLocalDataSource
import com.example.data.source.local.database.dao.ChannelDao
import javax.inject.Inject

class ChannelLocalDataSourceImpl @Inject constructor(
    private val dao: ChannelDao
) : ChannelLocalDataSource {
    override suspend fun saveChannels(channels: List<ChannelEntity>) = dao.insertAll(channels)
    override suspend fun getChannelsByChurchId(churchId: String): List<ChannelEntity> = dao.getChannelsByChurchId(churchId)
    override suspend fun deleteChannelsByChurch(churchId: String) = dao.deleteChannelsByChurch(churchId)
}