package com.example.domain.usecase.auth

import com.example.domain.model.User
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpMemberUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    data class MemberSignUpRequest(
        val email: String,
        val password: String,
        val name: String,
        val churchId: String
    )

    suspend operator fun invoke(request: MemberSignUpRequest): Result<User> {
        return authRepository.signUpMember(request)
    }
}