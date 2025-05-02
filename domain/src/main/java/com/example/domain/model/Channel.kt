package com.example.domain.model

data class Channel(
    val id: String,
    val name: String,
    val ownerId: String,
    val subscribers: List<String>
)