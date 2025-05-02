package com.example.domain.usecase.auth

import com.example.domain.model.SignUpStatus
import com.example.domain.model.User
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * 현재 승인 상태를 확인하는 기능.
 */
class GetRootChannelSignUpStatusUseCase @Inject constructor(
    private val authRepsitory : AuthRepository
) {
    suspend operator fun invoke(userId: String): Result<SignUpStatus> {
        return authRepsitory.getRootChannelSignUpStatus(userId)
    }
}