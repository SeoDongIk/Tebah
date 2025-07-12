package com.example.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channels")
data class ChannelEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String?,
    val isOfficial: Boolean,
    val createdAt: Long,
    val churchId: String,
    val ownerId: String
)