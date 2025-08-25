package com.example.presentation.auth.state

import androidx.annotation.StringRes
import com.example.domain.model.UserRole

sealed interface SignUpSideEffect {
    data class Toast(@StringRes val messageRes: Int) : SignUpSideEffect
    data class NavigateToMain(val role: UserRole) : SignUpSideEffect
    object NavigateToCompleteScreen : SignUpSideEffect
}