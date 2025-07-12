package com.example.data.source.local.impl

import com.example.data.model.entity.UserEntity
import com.example.data.source.local.AuthLocalDataSource
import com.example.data.source.local.database.dao.UserDao
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor(
    private val dao: UserDao
) : AuthLocalDataSource {
    override suspend fun saveAuthUser(user: UserEntity) = dao.insert(user)
    override suspend fun getAuthUser(): UserEntity? = dao.getCurrentUser()
    override suspend fun clearAuthUser() = dao.clear()
}