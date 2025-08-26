package com.example.domain.repository

import com.example.domain.model.AuthSnapshot

interface AuthSessionRepository {
    suspend fun getSnapshot(): Result<AuthSnapshot>
    suspend fun refreshFromServer(): Result<AuthSnapshot>
    suspend fun signOut(): Result<Unit>
}
