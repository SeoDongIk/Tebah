package com.example.presentation.model

import com.example.domain.model.UserRole

sealed interface LoginSideEffect {
    data class NavigateToMainActivity(val role: UserRole) : LoginSideEffect
    data class Toast(val message: String) : LoginSideEffect
}