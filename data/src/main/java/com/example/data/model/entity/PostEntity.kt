package com.example.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: String,
    val authorId: String,
    val content: String,
    val createdAt: Long,
    val checkedUserIds: String, // JSON 직렬화
    val likedUserIds: String,
    val savedUserIds: String,
    val commentIds: String,
    val channelIds: String,
    val isNotice: Boolean
)