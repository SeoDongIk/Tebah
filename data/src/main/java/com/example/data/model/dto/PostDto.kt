package com.example.data.model.dto

import com.example.domain.model.PostType

data class PostDto(
    val id: String = "",
    val authorId: String = "",
    val authorName: String = "",
    val authorProfileUrl: String? = null,
    val content: String = "",
    val imageUrls: List<String> = emptyList(),
    val createdAt: Long = 0L,
    val type: String = "GLOBAL",
    val churchId: String? = null,
    val requestedChannelIds: List<String> = emptyList(),
    val approvedChannelIds: List<String> = emptyList(),
    val likeCount: Int = 0,
    val saveCount: Int = 0,
    val checkCount: Int = 0,
    val commentCount: Int = 0
)