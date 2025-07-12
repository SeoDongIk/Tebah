package com.example.data.source.local.datastore

import androidx.datastore.core.DataStore
import com.example.data.UserPreferences
import com.example.data.UserRoleProto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class DefaultAppPreferencesDataStore @Inject constructor(
    private val dataStore: DataStore<UserPreferences>
) : AppPreferencesDataStore {

    private val TAG = "AppPreferencesDataStore"

    override val userId: Flow<String?> = dataStore.data.map { prefs ->
        val userId = prefs.userId.ifBlank { null }
        Timber.tag(TAG).d("userId: $userId")
        userId
    }

    override val isAutoLogin: Flow<Boolean> = dataStore.data.map { prefs ->
        val autoLogin = prefs.isAutoLogin
        Timber.tag(TAG).d("isAutoLogin: $autoLogin")
        autoLogin
    }

    override val userRole: Flow<UserRoleProto> = dataStore.data.map { prefs ->
        val role = prefs.userRole
        Timber.tag(TAG).d("userRole: $role")
        role
    }

    override suspend fun updateUserInfo(id: String, isAutoLogin: Boolean, role: UserRoleProto) {
        Timber.tag(TAG).d("updateUserInfo() called with id=$id, isAutoLogin=$isAutoLogin, role=$role")
        dataStore.updateData { current ->
            current.toBuilder()
                .setUserId(id)
                .setIsAutoLogin(isAutoLogin)
                .setUserRole(role)
                .build()
        }
    }

    override suspend fun clear() {
        Timber.tag(TAG).d("clear() called")
        dataStore.updateData {
            it.toBuilder()
                .clearUserId()
                .clearIsAutoLogin()
                .clearUserRole()
                .build()
        }
    }
}