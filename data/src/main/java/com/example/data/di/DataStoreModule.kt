package com.example.data.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.data.source.preference.UserPreferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(context: Context) =
        PreferenceDataStoreFactory.create(
            scope = CoroutineScope(Dispatchers.IO)
        ) {
        context.preferencesDataStoreFile("user_prefs")
    }

    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(dataStore: androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences>): UserPreferencesDataStore =
        UserPreferencesDataStore(dataStore)
}