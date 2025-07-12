package com.example.data.source.local

import com.example.data.model.entity.UserEntity

interface AuthLocalDataSource {
    suspend fun saveAuthUser(user: UserEntity)
    suspend fun getAuthUser(): UserEntity?
    suspend fun clearAuthUser()
}