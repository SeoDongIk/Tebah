package com.example.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.data.UserPreferences
import com.example.data.source.local.datastore.DefaultAppPreferencesDataStore
import com.example.data.source.local.datastore.AppPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
            serializer = AppPreferencesSerializer,
            produceFile = { context.dataStoreFile("user_prefs.pb") }
        )
    }

}