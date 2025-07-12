package com.example.domain.usecase.notification

import com.example.domain.model.Notification
import com.example.domain.repository.NotificationRepository
import javax.inject.Inject

class GetUnreadNotificationsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(): Result<List<Notification>> {
        return notificationRepository.getUnreadNotifications()
    }
}