package com.example.domain.repository

import com.example.domain.model.AdminSignUpRequest
import com.example.domain.model.MemberSignUpRequest
import com.example.domain.model.RootChannelSignUpRequest
import com.example.domain.model.SignInResult
import com.example.domain.model.SignUpStatus
import com.example.domain.model.User
import com.example.domain.model.UserRole

interface AuthRepository {
    suspend fun isLoggedIn(): Boolean
    suspend fun isAutoLogin(): Boolean
    suspend fun getCurrentUserRole(): UserRole
    suspend fun checkEmailExists(email: String): Boolean
    suspend fun signUpMember(request: MemberSignUpRequest): Result<User>
    suspend fun signUpAdmin(request: AdminSignUpRequest): Result<User>
    suspend fun signIn(email: String, password: String, autoLogin: Boolean): Result<SignInResult>

    suspend fun getRootChannelSignUpStatus(userId: String): Result<SignUpStatus>
    suspend fun isSignedIn(): Boolean
    suspend fun linkSocialAccount(provider: String, token: String): Result<Unit>
    suspend fun requestRootChannelSignUp(request: RootChannelSignUpRequest): Result<Unit>
    suspend fun signInWithEmail(email: String, password: String): Result<User>
    suspend fun signInWithSocial(provider: String, token: String): Result<User>
    suspend fun signOut(): Result<Unit>
    suspend fun signUpWithEmail(email: String, password: String, nickname: String): Result<User>
}