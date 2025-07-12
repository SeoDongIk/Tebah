package com.example.domain.model

data class Comment(
    val id: String,
    val postId: String,
    val userId: String,
    val userName: String,
    val userProfileUrl: String?,
    val content: String,
    val createdAt: Long
)