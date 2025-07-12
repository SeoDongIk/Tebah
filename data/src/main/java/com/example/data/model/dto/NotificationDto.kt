package com.example.data.model.dto

import com.example.domain.model.NotificationType
import kotlinx.serialization.Serializable

@Serializable
data class NotificationDto(
    val id: String,                          // Firestore 문서 ID 등
    val userId: String,                      // 알림 수신자 UID
    val title: String,
    val body: String,
    val type: NotificationType,              // FOLLOW, COMMENT 등
    val createdAt: Long,                     // 타임스탬프
    val data: Map<String, String> = emptyMap() // FCM data payload용 부가 정보
)