package com.example.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.entity.NotificationEntity

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notifications: List<NotificationEntity>)

    @Query("SELECT * FROM notifications WHERE userId = :userId")
    suspend fun getNotificationsByUserId(userId: String): List<NotificationEntity>

    @Query("DELETE FROM notifications WHERE id = :notificationId")
    suspend fun deleteNotificationById(notificationId: String)
}