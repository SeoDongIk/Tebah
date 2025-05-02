package com.example.domain.model

data class NoticeRequest(
    val postId: String,
    val channelId: String,
    val requestedAt: Long,
    val approved: Boolean
)