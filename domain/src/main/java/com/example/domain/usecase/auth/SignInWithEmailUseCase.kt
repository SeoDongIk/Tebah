package com.example.domain.usecase.auth

import com.example.domain.model.RootChannelSignUpRequest
import com.example.domain.model.User
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * 이메일 로그인을 수행하고 토큰을 저장하는 기능.
 */
class SignInWithEmailUseCase @Inject constructor(
    private val authRepsitory : AuthRepository
)  {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        return authRepsitory.signInWithEmail(email, password)
    }
}