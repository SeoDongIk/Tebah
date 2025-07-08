package com.example.domain.usecase.auth

import com.example.domain.model.User
import com.example.domain.repository.AuthRepository
import javax.inject.Inject


class CheckAutoLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return authRepository.isLoggedIn()
    }
}