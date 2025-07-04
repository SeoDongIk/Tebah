package com.example.data.repository

import com.example.domain.model.RootChannelSignUpRequest
import com.example.domain.model.SignUpStatus
import com.example.domain.model.User
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(

) :AuthRepository {

    override suspend fun autoSignIn(): Result<User> {
        TODO("Not yet implemented")
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