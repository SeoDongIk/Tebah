package com.example.data.repository

import com.example.data.UserRoleProto
import com.example.data.mapper.toDomain
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
            // 1. Firebase Auth 이메일/비밀번호 회원가입
            val result = firebaseAuth.createUserWithEmailAndPassword(request.adminEmail, request.adminPassword).await()
            val firebaseUser = result.user ?: return Result.failure(Exception("Admin user creation failed"))

            // 2. Firestore 인스턴스 및 문서 참조 생성
            val firestore = FirebaseFirestore.getInstance()
            val churchRef = firestore.collection("churches").document()  // 새 문서 (자동 ID)
            val userRef = firestore.collection("users").document(firebaseUser.uid)

            val churchId = churchRef.id

            // 3. 데이터 모델 생성
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

            // 4. Firestore WriteBatch로 원자적 저장
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
            // 1. Firebase Auth 회원가입
            val result = firebaseAuth.createUserWithEmailAndPassword(request.email, request.password).await()
            val firebaseUser = result.user ?: return Result.failure(Exception("Member user creation failed"))

            // 2. Firestore 인스턴스 및 유저 문서 참조
            val firestore = FirebaseFirestore.getInstance()
            val userRef = firestore.collection("users").document(firebaseUser.uid)

            // 3. User 데이터 모델 생성 (role은 MEMBER, churchId는 회원가입 시 주어진 값 혹은 null)
            val user = User(
                id = firebaseUser.uid,
                email = request.email,
                name = request.name,
                role = UserRole.MEMBER,
                churchId = request.churchId,
                profile = null
            )

            // 4. Firestore에 저장
            userRef.set(user).await()

            // TODO: 룸에 캐싱해두기

            Timber.tag("AuthRepositoryImpl").d("signUpMember success for uid=${firebaseUser.uid}")
            Result.success(user)
        } catch (e: Exception) {
            Timber.tag("AuthRepositoryImpl").e(e, "signUpMember failed")
            Result.failure(e)
        }
    }

    override suspend fun signIn(email: String, password: String): Result<SignInResult> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: return Result.failure(Exception("로그인 실패"))

            // Firestore에서 역할 읽기
            val firestore = FirebaseFirestore.getInstance()
            val userDoc = firestore.collection("users").document(firebaseUser.uid).get().await()
            val roleString = userDoc.getString("role")
            val role = UserRole.fromString(roleString)

            // TODO: 룸에 캐싱해두기

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