package com.example.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import com.example.data.ApprovalProto
import com.example.data.UserPreferences
import com.example.data.UserRoleProto
import com.example.data.model.dto.ChurchDto
import com.example.data.model.dto.UserDto
import com.example.data.source.local.datastore.AppPreferencesSerializer
import com.example.data.source.local.datastore.DefaultAppPreferencesDataStore
import com.example.data.source.remote.AuthRemoteDataSource
import com.example.domain.model.AdminSignUpRequest
import com.example.domain.usecase.auth.SignUpMemberUseCase
import com.example.domain.model.UserRole
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import java.io.File

private class FakeAuthRemoteDataSource : AuthRemoteDataSource {
    var signInResult: Result<UserDto> = Result.success(UserDto())
    var signOutResult: Result<Unit> = Result.success(Unit)

    override suspend fun signIn(email: String, password: String): Result<UserDto> = signInResult
    override suspend fun signOut(): Result<Unit> = signOutResult

    override suspend fun signUpAdmin(request: AdminSignUpRequest): Result<UserDto> = throw NotImplementedError()
    override suspend fun signUpMember(request: SignUpMemberUseCase.MemberSignUpRequest): Result<UserDto> = throw NotImplementedError()
    override suspend fun checkEmailExists(email: String): Boolean = false
    override suspend fun getUserById(userId: String): UserDto? = null
    override suspend fun saveUser(userDto: UserDto) {}
    override suspend fun saveChurch(churchDto: ChurchDto) {}
    override suspend fun createUser(email: String, password: String): String = ""
    override suspend fun isLoggedIn(): Boolean = false
    override suspend fun getCurrentUserRole(): UserRole = UserRole.GUEST
    override suspend fun signInAnonymously(): Result<Unit> = Result.success(Unit)
}

private fun createDataStore(): DefaultAppPreferencesDataStore {
    val file = File.createTempFile("test", ".pb").apply { deleteOnExit() }
    val dataStore: DataStore<UserPreferences> =
        DataStoreFactory.create(serializer = AppPreferencesSerializer) { file }
    return DefaultAppPreferencesDataStore(dataStore)
}

class AuthRepositoryImplTest {

    @Test
    fun signOut_success_clearsPreferences() = runBlocking {
        val remote = FakeAuthRemoteDataSource().apply {
            signOutResult = Result.success(Unit)
        }
        val prefs = createDataStore()
        prefs.updateUserInfo("id", true, UserRoleProto.MEMBER, ApprovalProto.APPROVED, 0L)

        val repo = AuthRepositoryImpl(remote, prefs)
        val result = repo.signOut()

        assertTrue(result.isSuccess)
        assertNull(prefs.userId.first())
        assertFalse(prefs.isAutoLogin.first())
    }

    @Test
    fun signOut_failure_keepsPreferences() = runBlocking {
        val remote = FakeAuthRemoteDataSource().apply {
            signOutResult = Result.failure(Exception("network"))
        }
        val prefs = createDataStore()
        prefs.updateUserInfo("id", true, UserRoleProto.MEMBER, ApprovalProto.APPROVED, 0L)

        val repo = AuthRepositoryImpl(remote, prefs)
        val result = repo.signOut()

        assertTrue(result.isFailure)
        assertEquals("id", prefs.userId.first())
        assertTrue(prefs.isAutoLogin.first())
    }

    @Test
    fun signIn_failure_propagatesError() = runBlocking {
        val remote = FakeAuthRemoteDataSource().apply {
            signInResult = Result.failure(Exception("invalid"))
        }
        val prefs = createDataStore()
        val repo = AuthRepositoryImpl(remote, prefs)

        val result = repo.signIn("email", "pwd", false)

        assertTrue(result.isFailure)
        assertEquals("invalid", result.exceptionOrNull()?.message)
    }
}

