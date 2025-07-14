package com.example.presentation.splash.state

sealed class SplashUiState {
    object Loading : SplashUiState()
    object GoToAdmin : SplashUiState()
    object GoToMember : SplashUiState()
    object GoToLogin : SplashUiState()
    object GoToStart : SplashUiState()
    object Error : SplashUiState()
}