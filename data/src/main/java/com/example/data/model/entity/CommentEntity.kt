package com.example.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey val id: String,
    val postId: String,
    val userId: String,
    val userName: String,
    val userProfileImageUrl: String?,
    val content: String,
    val createdAt: Long
)