package com.example.domain.repository

import com.example.domain.model.Notification

interface NotificationRepository {
    suspend fun deleteNotifications(notificationIds: List<String>): Result<Unit>
    suspend fun getAllNotifications(): Result<List<Notification>>
    suspend fun getUnreadNotifications(): Result<List<Notification>>
    suspend fun markNotificationAsRead(notificationId: String): Result<Unit>
}