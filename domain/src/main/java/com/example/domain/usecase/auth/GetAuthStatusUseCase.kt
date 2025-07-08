package com.example.domain.usecase.auth

import com.example.domain.model.AuthStatus
import javax.inject.Inject

class GetAuthStatusUseCase @Inject constructor(
    private val checkAutoLoginUseCase: CheckAutoLoginUseCase,
    private val checkUserRoleUseCase: CheckUserRoleUseCase
) {
    suspend operator fun invoke(): AuthStatus {
        val isLoggedIn = checkAutoLoginUseCase()
        val role = if (isLoggedIn) checkUserRoleUseCase() else null
        return AuthStatus(isLoggedIn, role)
    }
}