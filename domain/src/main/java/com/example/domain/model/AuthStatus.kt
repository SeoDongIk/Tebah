package com.example.domain.model

data class AuthStatus(
    val isLoggedIn: Boolean,
    val isAutoLogin: Boolean,
    val role: UserRole?
)