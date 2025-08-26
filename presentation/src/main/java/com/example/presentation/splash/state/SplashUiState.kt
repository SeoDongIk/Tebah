package com.example.presentation.splash.state

import androidx.annotation.StringRes

sealed class SplashUiState {
    object Loading : SplashUiState()
    object Start : SplashUiState()
    data class Error(@StringRes val messageRes: Int) : SplashUiState()
}