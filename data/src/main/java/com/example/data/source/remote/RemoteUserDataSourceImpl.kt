package com.example.data.source.remote

import com.example.domain.model.UserProfileUpdateRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : RemoteUserDataSource {

//    override suspend fun signUp(user: User): Result<Unit> = runCatching {
//        firestore.collection("users").document(user.id).set(user).await()
//    }
//
//    override suspend fun getUser(uid: String): Result<User> = runCatching {
//        firestore.collection("users").document(uid).get().await().toObject(User::class.java) ?: throw Exception("User not found")
//    }
//
//    override suspend fun updateUserProfile(updateRequest: UserProfileUpdateRequest): Result<Unit> = runCatching {
//        firestore.collection("users").document(updateRequest.userId).update(updateRequest.toMap()).await()
//    }
}