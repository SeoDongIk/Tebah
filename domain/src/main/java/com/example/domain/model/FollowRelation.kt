package com.example.domain.model

data class FollowRelation(
    val fromUserId: String,
    val toUserId: String,
    val followedAt: Long
)