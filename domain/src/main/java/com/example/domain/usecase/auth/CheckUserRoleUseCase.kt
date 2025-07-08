package com.example.domain.usecase.auth

import com.example.domain.model.UserRole
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class CheckUserRoleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): UserRole {
        return authRepository.getCurrentUserRole()
    }
}