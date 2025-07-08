package com.example.domain.usecase.auth

import com.example.domain.model.RootChannelSignUpRequest
import com.example.domain.model.User
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * 구글, 카카오 등의 토큰으로 로그인 요청 후, 내부 토큰을 저장하는 기능.
 */
class SignInWithSocialUseCase @Inject constructor(
    private val authRepsitory : AuthRepository
) {
    suspend operator fun invoke(provider: String, token: String): Result<User> {
        return authRepsitory.signInWithSocial(provider, token)
    }
}