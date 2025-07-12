package com.example.data.source.local.datastore

import com.example.data.UserRoleProto
import kotlinx.coroutines.flow.Flow

interface AppPreferencesDataStore {
    val userId: Flow<String?>
    val isAutoLogin: Flow<Boolean>
    val userRole: Flow<UserRoleProto>

    suspend fun updateUserInfo(id: String, isAutoLogin: Boolean, role: UserRoleProto)
    suspend fun clear()
}