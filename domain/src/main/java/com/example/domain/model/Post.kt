package com.example.domain.model

data class Post(
    val id: String,
    val authorId: String,
    val authorName: String,
    val authorProfileUrl: String?,
    val content: String,
    val imageUrls: List<String>,
    val createdAt: Long,
    val type: PostType,
    val churchId: String?,
    val requestedChannelIds: List<String>,
    val approvedChannelIds: List<String>,
    val likeCount: Int,
    val saveCount: Int,
    val checkCount: Int,
    val commentCount: Int
)