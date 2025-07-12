package com.example.data.source.local.impl

import com.example.data.model.entity.UserEntity
import com.example.data.source.local.UserLocalDataSource
import com.example.data.source.local.database.dao.UserDao
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao
) : UserLocalDataSource {

    override suspend fun saveUser(user: UserEntity) {
        userDao.insert(user)
    }

    override suspend fun getUserById(userId: String): UserEntity? {
        return userDao.getUserById(userId)
    }

    override suspend fun deleteUser(userId: String) {
        userDao.deleteById(userId)
    }

    override suspend fun getAuthUser(): UserEntity? {
        return userDao.getCurrentUser()
    }
}