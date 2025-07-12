package com.example.data.di

import com.example.data.source.local.AuthLocalDataSource
import com.example.data.source.local.PostLocalDataSource
import com.example.data.source.local.UserLocalDataSource
import com.example.data.source.local.datastore.AppPreferencesDataStore
import com.example.data.source.local.datastore.DefaultAppPreferencesDataStore
import com.example.data.source.local.impl.AuthLocalDataSourceImpl
import com.example.data.source.local.impl.PostLocalDataSourceImpl
import com.example.data.source.local.impl.UserLocalDataSourceImpl
import com.example.data.source.remote.AuthRemoteDataSource
import com.example.data.source.remote.ChannelRemoteDataSource
import com.example.data.source.remote.CommentRemoteDataSource
import com.example.data.source.remote.NotificationRemoteDataSource
import com.example.data.source.remote.PostRemoteDataSource
import com.example.data.source.remote.UserRemoteDataSource
import com.example.data.source.remote.impl.AuthRemoteDataSourceImpl
import com.example.data.source.remote.impl.ChannelRemoteDataSourceImpl
import com.example.data.source.remote.impl.CommentRemoteDataSourceImpl
import com.example.data.source.remote.impl.NotificationRemoteDataSourceImpl
import com.example.data.source.remote.impl.PostRemoteDataSourceImpl
import com.example.data.source.remote.impl.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    // 🔹 Local DataSources
    @Binds
    abstract fun bindAuthLocalDataSource(
        impl: AuthLocalDataSourceImpl
    ): AuthLocalDataSource

    @Binds
    abstract fun bindUserLocalDataSource(
        impl: UserLocalDataSourceImpl
    ): UserLocalDataSource

    @Binds
    abstract fun bindPostLocalDataSource(
        impl: PostLocalDataSourceImpl
    ): PostLocalDataSource

    @Binds
    abstract fun bindDefaultAppPreferencesDataStore(
        impl: DefaultAppPreferencesDataStore
    ): AppPreferencesDataStore

    // 🔹 Remote DataSources
    @Binds
    abstract fun bindAuthRemoteDataSource(
        impl: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource

    @Binds
    abstract fun bindUserRemoteDataSource(
        impl: UserRemoteDataSourceImpl
    ): UserRemoteDataSource

    @Binds
    abstract fun bindChannelRemoteDataSource(
        impl: ChannelRemoteDataSourceImpl
    ): ChannelRemoteDataSource

    @Binds
    abstract fun bindPostRemoteDataSource(
        impl: PostRemoteDataSourceImpl
    ): PostRemoteDataSource

    @Binds
    abstract fun bindCommentRemoteDataSource(
        impl: CommentRemoteDataSourceImpl
    ): CommentRemoteDataSource

    @Binds
    abstract fun bindNotificationRemoteDataSource(
        impl: NotificationRemoteDataSourceImpl
    ): NotificationRemoteDataSource

}