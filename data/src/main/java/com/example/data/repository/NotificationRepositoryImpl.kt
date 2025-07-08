package com.example.data.repository

import com.example.domain.model.Notification
import com.example.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(

) :NotificationRepository {
    override suspend fun deleteNotifications(notificationIds: List<String>): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllNotifications(): Result<List<Notification>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUnreadNotifications(): Result<List<Notification>> {
        TODO("Not yet implemented")
    }

    override suspend fun markNotificationAsRead(notificationId: String): Result<Unit> {
        TODO("Not yet implemented")
    }
}