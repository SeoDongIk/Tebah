package com.example.data.source.remote.impl

import com.example.data.model.dto.NotificationDto
import com.example.data.source.remote.NotificationRemoteDataSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NotificationRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : NotificationRemoteDataSource {
    private val notificationsCollection = firestore.collection("notifications")

    override suspend fun getNotificationsForUser(userId: String): Result<List<NotificationDto>> = runCatching {
        notificationsCollection
            .whereEqualTo("userId", userId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(NotificationDto::class.java)?.copy(id = it.id) }
    }

    override suspend fun deleteNotification(notificationId: String): Result<Unit> = runCatching {
        notificationsCollection.document(notificationId).delete().await()
    }
}