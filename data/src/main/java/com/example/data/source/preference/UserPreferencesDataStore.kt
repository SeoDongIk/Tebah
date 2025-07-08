package com.example.data.source.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesDataStore @Inject constructor(
    private val dataStore: DataStore<UserPreferences>
) {
    val userId: Flow<String?> = dataStore.data.map { it.userId.ifBlank { null } }
    val userToken: Flow<String?> = dataStore.data.map { it.userToken.ifBlank { null } }
    val userRole: Flow<String?> = dataStore.data.map { it.userRole.ifBlank { null } }

    suspend fun updateUserInfo(id: String, token: String, role: String) {
        dataStore.updateData { current ->
            current.toBuilder()
                .setUserId(id)
                .setUserToken(token)
                .setUserRole(role)
                .build()
        }
    }

    suspend fun clear() {
        dataStore.updateData {
            it.toBuilder()
                .clearUserId()
                .clearUserToken()
                .clearUserRole()
                .build()
        }
    }
}