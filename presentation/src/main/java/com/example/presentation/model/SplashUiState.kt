package com.example.presentation.model

sealed class SplashUiState {
    object Loading : SplashUiState()
    object GoToAdmin : SplashUiState()
    object GoToMember : SplashUiState()
    object GoToLogin : SplashUiState()
    object GoToStart : SplashUiState()
    object Error : SplashUiState()
}