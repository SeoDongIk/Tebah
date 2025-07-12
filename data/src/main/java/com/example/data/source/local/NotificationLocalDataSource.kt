package com.example.data.source.local

import com.example.data.model.entity.NotificationEntity

interface NotificationLocalDataSource {
    suspend fun saveNotifications(notifications: List<NotificationEntity>)
    suspend fun getNotificationsByUserId(userId: String): List<NotificationEntity>
    suspend fun deleteNotification(notificationId: String)
}