package com.example.data.source.remote

import com.example.domain.model.Notification
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteNotificationDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : RemoteNotificationDataSource {

//    override suspend fun sendNotification(notification: Notification): Result<Unit> = runCatching {
//        firestore.collection("notifications").document(notification.id).set(notification).await()
//    }
//
//    override suspend fun getNotifications(): Result<List<Notification>> = runCatching {
//        firestore.collection("notifications")
//            .get()
//            .await()
//            .documents.mapNotNull { it.toObject(Notification::class.java) }
//    }
}