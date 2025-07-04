package com.example.data.di

import com.example.data.source.local.LocalPostDataSource
import com.example.data.source.local.LocalPostDataSourceImpl
import com.example.data.source.preference.LocalUserPreferenceDataSource
import com.example.data.source.preference.LocalUserPreferenceDataSourceImpl
import com.example.data.source.remote.RemoteChannelDataSource
import com.example.data.source.remote.RemoteChannelDataSourceImpl
import com.example.data.source.remote.RemoteCommentDataSource
import com.example.data.source.remote.RemoteCommentDataSourceImpl
import com.example.data.source.remote.RemoteNotificationDataSource
import com.example.data.source.remote.RemoteNotificationDataSourceImpl
import com.example.data.source.remote.RemoteRootChannelDataSource
import com.example.data.source.remote.RemoteRootChannelDataSourceImpl
import com.example.data.source.remote.RemoteUserDataSource
import com.example.data.source.remote.RemoteUserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindLocalPostDataSource(
        impl: LocalPostDataSourceImpl
    ): LocalPostDataSource

    @Binds
    abstract fun bindLocalUserPreferenceDataSource(
        impl: LocalUserPreferenceDataSourceImpl
    ): LocalUserPreferenceDataSource

    @Binds
    abstract fun bindRemoteChannelDataSource(
        impl: RemoteChannelDataSourceImpl
    ): RemoteChannelDataSource

    @Binds
    abstract fun bindRemoteCommentDataSource(
        impl: RemoteCommentDataSourceImpl
    ): RemoteCommentDataSource

    @Binds
    abstract fun bindRemoteNotificationDataSource(
        impl: RemoteNotificationDataSourceImpl
    ): RemoteNotificationDataSource

    @Binds
    abstract fun bindRemoteRootChannelDataSource(
        impl: RemoteRootChannelDataSourceImpl
    ): RemoteRootChannelDataSource

    @Binds
    abstract fun bindRemoteUserDataSource(
        impl: RemoteUserDataSourceImpl
    ): RemoteUserDataSource
}