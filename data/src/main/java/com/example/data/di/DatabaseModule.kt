package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.dao.PostDao
import com.example.data.source.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()

    @Provides
    fun providePostDao(database: AppDatabase): PostDao =
        database.postDao()
}