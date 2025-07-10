package com.example.domain.usecase.auth

import com.example.domain.model.AdminSignUpRequest
import com.example.domain.model.User
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpAdminUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(request: AdminSignUpRequest): Result<User> {
        return authRepository.signUpAdmin(request)
    }
}