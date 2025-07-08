package com.example.domain.model

data class Comment(
    val id: String,
    val postId: String,
    val authorId: String,
    val content: String,
    val createdAt: Long
)