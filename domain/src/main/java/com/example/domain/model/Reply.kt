package com.example.domain.model

data class Reply(
    val id: String,
    val commentId: String,
    val authorId: String,
    val content: String,
    val createdAt: Long
)