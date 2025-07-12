package com.example.domain.model

data class PostCreateRequest(
    val authorId: String,
    val authorName: String, // 추가
    val authorProfileUrl: String?, // 추가
    val content: String,
    val imageUrls: List<String>,
    val type: PostType,
    val requestedChannelIds: List<String>,
    val churchId: String?,
    val createdAt: Long = System.currentTimeMillis()
)