package com.example.data.source.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesDataStore(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val KEY_USER_TOKEN = stringPreferencesKey("user_token")
    }

    val userToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[KEY_USER_TOKEN]
    }

    suspend fun saveUserToken(token: String) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_TOKEN] = token
        }
    }

    suspend fun clearUserToken() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_USER_TOKEN)
        }
    }
}