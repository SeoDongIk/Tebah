package com.example.domain.model

import java.time.LocalDateTime

data class Follow(
    val follower: User,
    val following: User,
    val createdAt: LocalDateTime
)