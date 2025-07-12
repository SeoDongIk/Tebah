package com.example.data.source.remote.impl

import com.example.data.model.dto.ChurchDto
import com.example.data.model.dto.UserDto
import com.example.data.source.remote.AuthRemoteDataSource
import com.example.domain.model.AdminPosition
import com.example.domain.model.AdminSignUpRequest
import com.example.domain.model.MemberSignUpRequest
import com.example.domain.model.UserRole
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRemoteDataSource {

    override suspend fun signUpAdmin(request: AdminSignUpRequest): Result<UserDto> {
        return try {
            val result = firebaseAuth
                .createUserWithEmailAndPassword(request.adminEmail, request.adminPassword)
                .await()

            val firebaseUser = result.user ?: return Result.failure(Exception("Admin user creation failed"))

            val churchRef = firestore.collection("churches").document()
            val userRef = firestore.collection("users").document(firebaseUser.uid)
            val churchId = churchRef.id

            val now = Timestamp.now()

            val church = ChurchDto(
                id = churchId,
                name = request.church.name,
                region = request.church.region.name,
                phone = request.church.phone,
                description = request.church.description,
                profileImageUrl = null,
                createdAt = now,
                adminId = firebaseUser.uid,
                adminName = request.adminName,
                adminPosition = request.adminPosition
                    .takeIf { it != AdminPosition.CUSTOM }
                    ?.name ?: (request.customPosition ?: "직접입력")
            )

            val userDto = UserDto(
                id = firebaseUser.uid,
                email = request.adminEmail,
                name = request.adminName,
                role = UserRole.ADMIN.name,
                isApproved = false,
                churchId = churchId,
                profile = null
            )

            val batch = firestore.batch()
            batch.set(churchRef, church)
            batch.set(userRef, userDto)
            batch.commit().await()

            Result.success(userDto)

        } catch (e: Exception) {
            Timber.tag("AuthRemoteDataSourceImpl").e(e, "signUpAdmin failed")
            Result.failure(e)
        }
    }

    override suspend fun signUpMember(request: MemberSignUpRequest): Result<UserDto> {
        return try {
            val result = firebaseAuth
                .createUserWithEmailAndPassword(request.email, request.password)
                .await()

            val firebaseUser = result.user ?: return Result.failure(Exception("Member user creation failed"))

            val userDto = UserDto(
                id = firebaseUser.uid,
                email = request.email,
                name = request.name,
                role = UserRole.MEMBER.name,
                churchId = request.churchId,
                isApproved = null,
                profile = null
            )

            firestore.collection("users").document(firebaseUser.uid).set(userDto).await()

            Result.success(userDto)

        } catch (e: Exception) {
            Timber.tag("AuthRemoteDataSourceImpl").e(e, "signUpMember failed")
            Result.failure(e)
        }
    }

    override suspend fun signIn(email: String, password: String): Result<UserDto> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: return Result.failure(Exception("로그인 실패"))

            val doc = firestore.collection("users").document(firebaseUser.uid).get().await()
            val userDto = doc.toObject(UserDto::class.java)
                ?: return Result.failure(Exception("User data not found"))

            Result.success(userDto)
        } catch (e: Exception) {
            Timber.tag("AuthRemoteDataSourceImpl").e(e, "signIn failed")
            Result.failure(e)
        }
    }

    override suspend fun signOut(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun checkEmailExists(email: String): Boolean {
        return try {
            val result = firebaseAuth.fetchSignInMethodsForEmail(email).await()
            result.signInMethods?.isNotEmpty() == true
        } catch (e: Exception) {
            Timber.tag("AuthRemoteDataSourceImpl").e(e, "checkEmailExists failed")
            false
        }
    }

    override suspend fun getUserById(userId: String): UserDto? {
        TODO("Not yet implemented")
    }

    override suspend fun saveUser(userDto: UserDto) {
        TODO("Not yet implemented")
    }

    override suspend fun saveChurch(churchDto: ChurchDto) {
        TODO("Not yet implemented")
    }

    override suspend fun createUser(email: String, password: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun getCurrentUserRole(): UserRole {
        val uid = firebaseAuth.currentUser?.uid ?: return UserRole.GUEST
        val doc = firestore.collection("users").document(uid).get().await()
        val roleString = doc.getString("role")
        return UserRole.fromString(roleString)
    }
}