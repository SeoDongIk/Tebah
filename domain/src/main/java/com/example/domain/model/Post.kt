package com.example.domain.model

data class Post(
    val id: String,
    val authorId: String,
    val title: String,
    val content: String,
    val imageUrls: List<String>,
    val isNotice: Boolean,
    val noticeRequestId: String?, // 공지 요청과 연결됨
    val createdAt: Long,
    val likedUserIds: List<String>,
    val checkedUserIds: List<String>,
    val isSavedLocally: Boolean
)
