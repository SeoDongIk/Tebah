package com.example.data.repository

import com.example.data.UserRoleProto
import com.example.data.mapper.toDomain
import com.example.data.mapper.toProto
import com.example.data.source.preference.UserPreferencesDataStore
import com.example.domain.model.AdminSignUpRequest
import com.example.domain.model.Church
import com.example.domain.model.MemberSignUpRequest
import com.example.domain.model.RootChannelSignUpRequest
import com.example.domain.model.SignInResult
import com.example.domain.model.SignUpStatus
import com.example.domain.model.User
import com.example.domain.model.UserRole
import com.example.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val userDataSource: UserPreferencesDataStore
) :AuthRepository {
    override suspend fun isLoggedIn(): Boolean {
        val loggedIn = firebaseAuth.currentUser != null
        Timber.tag("AuthRepositoryImpl").d("isLoggedIn() called, result: $loggedIn")
        return loggedIn
    }

    override suspend fun isAutoLogin(): Boolean {
        val enabled = userDataSource.isAutoLogin.firstOrNull() ?: false
        Timber.tag("AuthRepositoryImpl").d("isAutoLoginEnabled() called, result: $enabled")
        return enabled
    }

    override suspend fun getCurrentUserRole(): UserRole {
        val roleProto = userDataSource.userRole.firstOrNull() ?: UserRoleProto.GUEST
        val role = roleProto.toDomain()
        Timber.tag("AuthRepositoryImpl").d("getCurrentUserRole() called, raw: $roleProto, mapped: $role")
        return role
    }

    override suspend fun checkEmailExists(email: String): Boolean {
        return try {
            val result = firebaseAuth.fetchSignInMethodsForEmail(email).await()
            val exists = result.signInMethods?.isNotEmpty() == true
            Timber.tag("AuthRepositoryImpl").d("checkEmailExists($email): $exists")
            exists
        } catch (e: Exception) {
            Timber.tag("AuthRepositoryImpl").e(e, "Failed to check email existence")
            false
        }
    }

    override suspend fun signUpAdmin(request: AdminSignUpRequest): Result<User> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(request.adminEmail, request.adminPassword).await()
            val firebaseUser = result.user ?: return Result.failure(Exception("Admin user creation failed"))

            val churchRef = firestore.collection("churches").document()
            val userRef = firestore.collection("users").document(firebaseUser.uid)

            val churchId = churchRef.id

            val church = Church(
                id = churchId,
                name = request.church.name,
                region = request.church.region,
                phone = request.church.phone,
                description = request.church.description
            )
            val user = User(
                id = firebaseUser.uid,
                email = request.adminEmail,
                name = request.adminName,
                role = UserRole.ADMIN,
                churchId = churchId,
                profile = null
            )

            val batch = firestore.batch()
            batch.set(churchRef, church)
            batch.set(userRef, user)
            batch.commit().await()

            // TODO: 룸에 캐싱해두기

            Timber.tag("AuthRepositoryImpl").d("signUpAdmin success for uid=${firebaseUser.uid}")
            Result.success(user)
        } catch (e: Exception) {
            Timber.tag("AuthRepositoryImpl").e(e, "signUpAdmin failed")
            Result.failure(e)
        }
    }

    override suspend fun signUpMember(request: MemberSignUpRequest): Result<User> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(request.email, request.password).await()
            val firebaseUser = result.user ?: return Result.failure(Exception("Member user creation failed"))

            val userRef = firestore.collection("users").document(firebaseUser.uid)

            val user = User(
                id = firebaseUser.uid,
                email = request.email,
                name = request.name,
                role = UserRole.MEMBER,
                churchId = request.churchId,
                profile = null
            )

            userRef.set(user).await()

            // TODO: 룸에 캐싱해두기

            Timber.tag("AuthRepositoryImpl").d("signUpMember success for uid=${firebaseUser.uid}")
            Result.success(user)
        } catch (e: Exception) {
            Timber.tag("AuthRepositoryImpl").e(e, "signUpMember failed")
            Result.failure(e)
        }
    }

    override suspend fun signIn(email: String, password: String, autoLogin: Boolean): Result<SignInResult> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: return Result.failure(Exception("로그인 실패"))

            val userDoc = firestore.collection("users").document(firebaseUser.uid).get().await()
            val roleString = userDoc.getString("role")
            val role = UserRole.fromString(roleString)

            userDataSource.updateUserInfo(
                id = firebaseUser.uid,
                isAutoLogin = autoLogin,
                role = role.toProto()
            )

            Result.success(SignInResult(role))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun getRootChannelSignUpStatus(userId: String): Result<SignUpStatus> {
        TODO("Not yet implemented")
    }

    override suspend fun isSignedIn(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun linkSocialAccount(provider: String, token: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun requestRootChannelSignUp(request: RootChannelSignUpRequest): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithEmail(email: String, password: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithSocial(provider: String, token: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun signUpWithEmail(
        email: String,
        password: String,
        nickname: String
    ): Result<User> {
        TODO("Not yet implemented")
    }

}