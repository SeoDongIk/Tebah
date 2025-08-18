package com.example.presentation.auth.state

import androidx.annotation.StringRes
import com.example.domain.model.UserRole

sealed interface SignInSideEffect {
    data class NavigateToMainActivity(val role: UserRole) : SignInSideEffect
    data class Toast(@StringRes val messageRes: Int) : SignInSideEffect
}