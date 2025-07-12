package com.example.domain.model

data class Channel(
    val id: String,
    val name: String,
    val description: String?,
    val isOfficial: Boolean,
    val createdAt: Long,
    val churchId: String,
    val ownerId: String
)