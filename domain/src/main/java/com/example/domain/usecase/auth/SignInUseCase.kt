package com.example.domain.usecase.auth

import com.example.domain.model.SignInResult
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, autoLogin: Boolean): Result<SignInResult> {
        return authRepository.signIn(email, password, autoLogin)
    }
}