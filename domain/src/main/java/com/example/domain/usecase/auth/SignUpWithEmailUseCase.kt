package com.example.domain.usecase.auth

import com.example.domain.model.User
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * 이메일, 비밀번호, 이름 등을 이용해 회원가입을 요청하는 기능.
 */
class SignUpWithEmailUseCase @Inject constructor(
    private val authRepsitory : AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, nickname: String): Result<User> {
        return authRepsitory.signUpWithEmail(email, password, nickname)
    }
}