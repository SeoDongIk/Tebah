package com.example.presentation.auth.state

sealed interface SignUpSideEffect {
    data class Toast(val message: String): SignUpSideEffect
    class NavigateToMainActivity(val isAdmin: Boolean) : SignUpSideEffect
    object NavigateToChurchSelect : SignUpSideEffect
    object NavigateToCompleteScreen : SignUpSideEffect
}