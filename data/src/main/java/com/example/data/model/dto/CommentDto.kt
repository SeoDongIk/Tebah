package com.example.data.model.dto

data class CommentDto(
    val id: String = "",
    val postId: String = "",
    val userId: String = "",
    val userName: String = "",
    val userProfileImageUrl: String? = null,
    val content: String = "",
    val createdAt: Long = 0L
)