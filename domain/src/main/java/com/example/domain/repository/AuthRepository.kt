package com.example.domain.repository

import com.example.domain.model.RootChannelSignUpRequest
import com.example.domain.model.SignUpStatus
import com.example.domain.model.User

interface AuthRepository {
    suspend fun autoSignIn(): Result<User>
    suspend fun getRootChannelSignUpStatus(userId: String): Result<SignUpStatus>
    suspend fun isSignedIn(): Boolean
    suspend fun linkSocialAccount(provider: String, token: String): Result<Unit>
    suspend fun requestRootChannelSignUp(request: RootChannelSignUpRequest): Result<Unit>
    suspend fun signInWithEmail(email: String, password: String): Result<User>
    suspend fun signInWithSocial(provider: String, token: String): Result<User>
    suspend fun signOut(): Result<Unit>
    suspend fun signUpWithEmail(email: String, password: String, nickname: String): Result<User>
}