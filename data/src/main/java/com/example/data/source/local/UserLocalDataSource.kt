package com.example.data.source.local

import com.example.data.model.entity.UserEntity

interface UserLocalDataSource {
    suspend fun saveUser(user: UserEntity)
    suspend fun getUserById(userId: String): UserEntity?
    suspend fun deleteUser(userId: String)
    suspend fun getAuthUser(): UserEntity?
}