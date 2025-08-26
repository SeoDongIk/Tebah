package com.example.presentation.splash.state

sealed class SplashNavEvent {
    data object GoToAdmin : SplashNavEvent()
    data object GoToMember : SplashNavEvent()
    data object GoToPending : SplashNavEvent()
    data object GoToLogin : SplashNavEvent()
}
