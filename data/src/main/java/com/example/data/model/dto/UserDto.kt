package com.example.data.model.dto

data class UserDto(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val role: String = "",
    val isApproved: Boolean? = null,
    val churchId: String = "",
    val profile: UserProfileDto? = null
)