package com.example.data.repository

import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntity
import com.example.data.source.local.NotificationLocalDataSource
import com.example.data.source.local.UserLocalDataSource
import com.example.data.source.remote.NotificationRemoteDataSource
import com.example.domain.model.Notification
import com.example.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val remote: NotificationRemoteDataSource,
    private val local: NotificationLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : NotificationRepository {
    override suspend fun deleteNotifications(notificationIds: List<String>): Result<Unit> {
        return runCatching {
            notificationIds.forEach { id ->
                remote.deleteNotification(id).getOrThrow()
                local.deleteNotification(id)
            }
        }
    }

    override suspend fun getAllNotifications(): Result<List<Notification>> {
        val user = userLocalDataSource.getAuthUser()
            ?: return Result.failure(IllegalStateException("User not logged in"))
        return remote.getNotificationsForUser(user.id)
            .onSuccess { dtos ->
                local.saveNotifications(dtos.map { it.toEntity() })
            }.map { dtos -> dtos.map { it.toDomain() } }
    }

    override suspend fun getUnreadNotifications(): Result<List<Notification>> {
        return getAllNotifications()
    }

    override suspend fun markNotificationAsRead(notificationId: String): Result<Unit> {
        return remote.deleteNotification(notificationId)
            .onSuccess { local.deleteNotification(notificationId) }
    }
}