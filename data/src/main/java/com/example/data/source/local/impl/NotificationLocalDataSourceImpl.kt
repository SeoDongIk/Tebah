package com.example.data.source.local.impl

import com.example.data.model.entity.NotificationEntity
import com.example.data.source.local.NotificationLocalDataSource
import com.example.data.source.local.database.dao.NotificationDao
import javax.inject.Inject

class NotificationLocalDataSourceImpl @Inject constructor(
    private val dao: NotificationDao
) : NotificationLocalDataSource {
    override suspend fun saveNotifications(notifications: List<NotificationEntity>) = dao.insertAll(notifications)
    override suspend fun getNotificationsByUserId(userId: String): List<NotificationEntity> = dao.getNotificationsByUserId(userId)
    override suspend fun deleteNotification(notificationId: String) = dao.deleteNotificationById(notificationId)
}