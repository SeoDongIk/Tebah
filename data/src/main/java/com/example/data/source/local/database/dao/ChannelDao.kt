package com.example.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.entity.ChannelEntity

@Dao
interface ChannelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(channels: List<ChannelEntity>)

    @Query("SELECT * FROM channels WHERE churchId = :churchId")
    suspend fun getChannelsByChurchId(churchId: String): List<ChannelEntity>

    @Query("DELETE FROM channels WHERE churchId = :churchId")
    suspend fun deleteChannelsByChurch(churchId: String)
}