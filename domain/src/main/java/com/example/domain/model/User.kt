package com.example.domain.model

data class User(
    val id: String,
    val email: String,
    val name: String,
    val role: UserRole,
    val isApproved: Boolean?,
    val churchId: String,
    val profile: UserProfile?
)