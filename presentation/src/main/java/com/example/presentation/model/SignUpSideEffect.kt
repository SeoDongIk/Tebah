package com.example.presentation.model

sealed interface SignUpSideEffect {
    data class Toast(val message: String): SignUpSideEffect
    class NavigateToMainActivity(val isAdmin: Boolean) : SignUpSideEffect
}