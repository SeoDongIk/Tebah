package com.example.domain.model

sealed class SplashDestination {
    data object AdminMain : SplashDestination()
    data object MemberMain : SplashDestination()
    data object PendingApproval : SplashDestination()
    data object Login : SplashDestination()
    data object Start : SplashDestination()
    data class Error(val reason: String) : SplashDestination()
}
