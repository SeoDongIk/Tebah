package com.example.domain.usecase.auth

import com.example.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * 현재 로그인된 상태인지 확인하는 기능.
 */
class IsSignedInUseCase @Inject constructor(
    private val authRepsitory : AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return authRepsitory.isSignedIn()
    }
}