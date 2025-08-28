package com.example.domain.usecase.auth

import com.example.domain.model.AdminPosition
import com.example.domain.model.ChurchInfo
import com.example.domain.model.User
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpAdminUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    data class AdminSignUpRequest(
        val church: ChurchInfo,
        val adminName: String,
        val adminEmail: String,
        val adminPassword: String,
        val adminPosition: AdminPosition,
        val customPosition: String? = null
    )

    suspend operator fun invoke(request: AdminSignUpRequest): Result<User> {
        return authRepository.signUpAdmin(request)
    }
}