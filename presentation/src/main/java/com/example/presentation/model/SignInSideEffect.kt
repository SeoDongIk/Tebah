package com.example.presentation.model

import com.example.domain.model.UserRole

sealed interface SignInSideEffect {
    data class NavigateToMainActivity(val role: UserRole) : SignInSideEffect
    data class Toast(val message: String) : SignInSideEffect
}