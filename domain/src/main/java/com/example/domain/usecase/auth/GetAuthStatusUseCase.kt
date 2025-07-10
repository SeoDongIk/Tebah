package com.example.domain.usecase.auth

import com.example.domain.model.AuthStatus
import javax.inject.Inject

class GetAuthStatusUseCase @Inject constructor(
    private val checkisLoggedInUseCase: CheckisLoggedInUseCase,
    private val checkAutoLoginUseCase: CheckAutoLoginUseCase,
    private val checkUserRoleUseCase: CheckUserRoleUseCase
) {
    suspend operator fun invoke(): AuthStatus {
        val isLoggedIn = checkisLoggedInUseCase()
        val isAutoLogin = checkAutoLoginUseCase()
        val role = checkUserRoleUseCase()
        return AuthStatus(isLoggedIn, isAutoLogin, role)
    }
}