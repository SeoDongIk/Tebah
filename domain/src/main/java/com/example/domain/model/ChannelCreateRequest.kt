package com.example.domain.model

data class ChannelCreateRequest(
    val name: String,
    val description: String?,
    val churchId: String,
    val ownerId: String
)