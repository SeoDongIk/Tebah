package com.example.data.source.remote

import com.example.data.model.dto.ChurchDto
import com.example.data.model.dto.UserDto
import com.example.domain.model.AdminSignUpRequest
import com.example.domain.model.MemberSignUpRequest
import com.example.domain.model.UserRole

interface AuthRemoteDataSource {
    suspend fun signUpAdmin(request: AdminSignUpRequest): Result<UserDto>
    suspend fun signUpMember(request: MemberSignUpRequest): Result<UserDto>
    suspend fun signIn(email: String, password: String): Result<UserDto>
    suspend fun signOut(): Result<Unit>
    suspend fun checkEmailExists(email: String): Boolean
    suspend fun getUserById(userId: String): UserDto?
    suspend fun saveUser(userDto: UserDto)
    suspend fun saveChurch(churchDto: ChurchDto)
    suspend fun createUser(email: String, password: String): String
    suspend fun isLoggedIn(): Boolean
    suspend fun getCurrentUserRole(): UserRole
}