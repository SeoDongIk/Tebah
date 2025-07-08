package com.example.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.data.source.preference.UserPreferencesDataStore
import com.example.data.source.preference.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideProtoDataStore(
        @ApplicationContext context: Context
    ): DataStore<UserPreferences> {
        return DataStoreFactory.create(
            serializer = UserPreferencesSerializer,
            produceFile = { context.dataStoreFile("user_prefs.pb") }
        )
    }

    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(dataStore: androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences>): UserPreferencesDataStore =
        UserPreferencesDataStore(dataStore)
}