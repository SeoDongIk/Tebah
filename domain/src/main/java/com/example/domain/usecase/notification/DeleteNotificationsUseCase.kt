package com.example.domain.usecase.notification

import com.example.domain.repository.NoticeRepository
import com.example.domain.repository.NotificationRepository
import javax.inject.Inject

class DeleteNotificationsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(notificationIds: List<String>): Result<Unit> {
        return notificationRepository.deleteNotifications(notificationIds)
    }
}