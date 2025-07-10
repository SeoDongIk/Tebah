package com.example.domain.usecase.auth

import com.example.domain.model.MemberSignUpRequest
import com.example.domain.model.User
import com.example.domain.model.UserRole
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpMemberUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(request: MemberSignUpRequest): Result<User> {
        return authRepository.signUpMember(request)
    }
}