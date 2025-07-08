package com.example.domain.usecase.auth

import com.example.domain.model.User
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * 토큰이 저장되어 있으면 자동 로그인을 시도하는 기능.
 */
class AutoSignInUseCase @Inject constructor(
    private val authRepsitory : AuthRepository
) {
    suspend operator fun invoke(): Result<User> {
        return authRepsitory.autoSignIn()
    }
}