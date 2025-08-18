package com.example.presentation.splash.state

import androidx.annotation.StringRes

sealed class SplashUiState {
    object Loading : SplashUiState()
    object GoToAdmin : SplashUiState()
    object GoToMember : SplashUiState()
    object GoToLogin : SplashUiState()
    object GoToStart : SplashUiState()
    data class Error(@StringRes val messageRes: Int) : SplashUiState()
}