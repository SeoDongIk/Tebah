package com.example.domain.usecase.auth

import com.example.domain.model.User
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * 토큰 제거 및 로그아웃을 처리하는 기능.
 */
class SignOutUseCase @Inject constructor(
    private val authRepsitory : AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return authRepsitory.signOut()
    }
}