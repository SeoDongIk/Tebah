package com.example.domain.model

data class Notification(
    val id: String,
    val userId: String,
    val content: String,
    val type: NotificationType,
    val relatedPostId: String?,
    val timestamp: Long
)