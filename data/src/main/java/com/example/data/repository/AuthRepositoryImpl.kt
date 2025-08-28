package com.example.data.repository

import com.example.data.ApprovalProto
import com.example.data.UserRoleProto
import com.example.data.mapper.toDomain
import com.example.data.mapper.toProto
import com.example.data.source.local.datastore.DefaultAppPreferencesDataStore
import com.example.data.source.remote.AuthRemoteDataSource
import com.example.domain.model.User
import com.example.domain.model.UserRole
import com.example.domain.repository.AuthRepository
import com.example.domain.usecase.auth.SignUpAdminUseCase
import com.example.domain.usecase.auth.SignUpMemberUseCase
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val userPreferences: DefaultAppPreferencesDataStore
) :AuthRepository {
    override suspend fun isLoggedIn(): Boolean {
        val result = authRemoteDataSource.isLoggedIn()
        Timber.tag("AuthRepositoryImpl").d("isLoggedIn: $result")
        return result
    }

    override suspend fun isAutoLogin(): Boolean {
        val enabled = userPreferences.isAutoLogin.firstOrNull() ?: false
        Timber.tag("AuthRepositoryImpl").d("isAutoLogin: $enabled")
        return enabled
    }

    override suspend fun getCurrentUserRole(): UserRole {
        val roleProto = userPreferences.userRole.firstOrNull() ?: UserRoleProto.USER_ROLE_UNSPECIFIED
        val role = roleProto.toDomain()
        Timber.tag("AuthRepositoryImpl").d("getCurrentUserRole: $role")
        return role
    }

    override suspend fun checkEmailExists(email: String): Boolean {
        return authRemoteDataSource.checkEmailExists(email)
    }

    override suspend fun signUpAdmin(request: SignUpAdminUseCase.AdminSignUpRequest): Result<User> {
        return authRemoteDataSource
            .signUpAdmin(request)
            .map { it.toDomain() }
    }

    override suspend fun signUpMember(request: SignUpMemberUseCase.MemberSignUpRequest): Result<User> {
        return authRemoteDataSource
            .signUpMember(request)
            .map { it.toDomain() }
    }

    override suspend fun signIn(email: String, password: String, autoLogin: Boolean): Result<UserRole> {
        return try {
            val result = authRemoteDataSource.signIn(email, password)
            if (result.isSuccess) {
                val userDto = result.getOrNull()
                userDto?.let {
                    val approvalProto = when (it.isApproved) {
                        true -> ApprovalProto.APPROVED
                        false -> ApprovalProto.PENDING
                        null -> ApprovalProto.APPROVAL_PROTO_UNSPECIFIED
                    }
                    userPreferences.updateUserInfo(
                        id = it.id,
                        isAutoLogin = autoLogin,
                        role = UserRole.fromString(it.role).toProto(),
                        approval = approvalProto,
                        lastSyncedAt = System.currentTimeMillis()
                    )
                    return Result.success(UserRole.fromString(it.role))
                }
            }
            Result.failure(Exception("UserDto is null"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOut(): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun signInAnonymously(): Result<Unit> {
        return authRemoteDataSource.signInAnonymously()
    }

}