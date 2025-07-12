package com.example.domain.model

data class PostPreviewItem(
    val id: String,
    val authorName: String,
    val authorProfileUrl: String?,
    val content: String,
    val imageUrls: List<String>,
    val createdAt: Long,
    val likeCount: Int,
    val saveCount: Int,
    val checkCount: Int
)