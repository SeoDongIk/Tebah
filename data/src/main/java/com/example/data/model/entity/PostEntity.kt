package com.example.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: String,
    val authorId: String,
    val authorName: String,
    val authorProfileUrl: String?,
    val content: String,
    val imageUrls: List<String>,
    val createdAt: Long,
    val type: String,
    val churchId: String?,
    val requestedChannelIds: List<String>,
    val approvedChannelIds: List<String>,
    val likeCount: Int,
    val saveCount: Int,
    val checkCount: Int,
    val commentCount: Int
)