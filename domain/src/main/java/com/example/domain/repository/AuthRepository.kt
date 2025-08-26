package com.example.domain.repository

import com.example.domain.model.AdminSignUpRequest
import com.example.domain.model.User
import com.example.domain.model.UserRole
import com.example.domain.usecase.auth.SignUpMemberUseCase

interface AuthRepository {
    suspend fun isLoggedIn(): Boolean
    suspend fun isAutoLogin(): Boolean
    suspend fun getCurrentUserRole(): UserRole
    suspend fun checkEmailExists(email: String): Boolean
    suspend fun signUpMember(request: SignUpMemberUseCase.MemberSignUpRequest): Result<User>
    suspend fun signUpAdmin(request: AdminSignUpRequest): Result<User>
    suspend fun signIn(email: String, password: String, autoLogin: Boolean): Result<UserRole>
    suspend fun signOut(): Result<Unit>
    suspend fun signInAnonymously(): Result<Unit>
}