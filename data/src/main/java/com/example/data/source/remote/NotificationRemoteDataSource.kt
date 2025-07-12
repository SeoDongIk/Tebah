package com.example.data.source.remote

import com.example.data.model.dto.NotificationDto

interface NotificationRemoteDataSource {
    suspend fun getNotificationsForUser(userId: String): Result<List<NotificationDto>>
    suspend fun deleteNotification(notificationId: String): Result<Unit>
}