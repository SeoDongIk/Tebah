package com.example.data.model.dto

data class ChannelDto(
    val id: String = "",
    val name: String = "",
    val description: String? = null,
    val isOfficial: Boolean = false,
    val createdAt: Long = 0L,
    val churchId: String = "",
    val ownerId: String = ""
)