package com.example.data.mapper

import com.example.data.model.dto.NotificationDto
import com.example.data.model.entity.NotificationEntity
import com.example.domain.model.Notification
import com.example.domain.model.NotificationType

fun NotificationDto.toEntity(): NotificationEntity = NotificationEntity(
    id = id,
    userId = userId,
    title = title,
    message = body,
    receivedAt = createdAt
)

fun NotificationDto.toDomain(): Notification = Notification(
    id = id,
    userId = userId,
    content = body,
    type = type,
    relatedPostId = data["postId"],
    timestamp = createdAt
)

fun NotificationEntity.toDomain(): Notification = Notification(
    id = id,
    userId = userId,
    content = message,
    type = NotificationType.SYSTEM,
    relatedPostId = null,
    timestamp = receivedAt
)
