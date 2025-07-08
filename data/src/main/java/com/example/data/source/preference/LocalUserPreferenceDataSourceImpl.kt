package com.example.data.source.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LocalUserPreferenceDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LocalUserPreferenceDataSource {

//    override suspend fun saveUserProfile(user: User) {
//        dataStore.edit { prefs ->
//            prefs[stringPreferencesKey("user_id")] = user.id
//            prefs[stringPreferencesKey("user_name")] = user.name
//            // 기타 필요한 필드 추가
//        }
//    }
//
//    override suspend fun getUserProfile(): User? {
//        val prefs = dataStore.data.first()
//        val id = prefs[stringPreferencesKey("user_id")] ?: return null
//        val name = prefs[stringPreferencesKey("user_name")] ?: ""
//        return User(
//            id = id,
//            name = name,
//            email = TODO(),
//            isRootChannelManager = TODO()
//        )
//    }
}