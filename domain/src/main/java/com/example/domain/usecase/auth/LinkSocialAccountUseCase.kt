package com.example.domain.usecase.auth

import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class LinkSocialAccountUseCase @Inject constructor(
    private val authRepsitory : AuthRepository
) {
    suspend operator fun invoke(provider: String, token: String): Result<Unit> {
        return authRepsitory.linkSocialAccount(provider, token)
    }
}