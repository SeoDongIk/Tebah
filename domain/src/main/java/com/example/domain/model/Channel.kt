package com.example.domain.model

data class Channel(
    val id: String,
    val name: String,
    val type: ChannelType, // 범교회 / 교회내부 / 자유
    val isOfficial: Boolean, // ✅ 공식 여부
    val owner: User,         // 만든 사람
    val approvedBy: User? = null // 관리자 승인자
)