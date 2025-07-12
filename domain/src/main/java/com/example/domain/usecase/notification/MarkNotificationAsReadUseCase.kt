package com.example.domain.usecase.notification

import com.example.domain.repository.NotificationRepository
import javax.inject.Inject

class MarkNotificationAsReadUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(notificationId: String): Result<Unit> {
        return notificationRepository.markNotificationAsRead(notificationId)
    }
}