package com.example.presentation.auth.state

import com.example.domain.model.UserRole

data class SignInState(
    val id: String = "",
    val password: String = "",
    val role: UserRole? = null,
    val autoLogin: Boolean = false,
    val isLoginEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val idError: Int? = null, // stringResId, null이면 정상
    val passwordError: Int? = null // stringResId, null이면 정상
)