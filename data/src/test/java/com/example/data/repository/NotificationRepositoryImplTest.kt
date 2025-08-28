package com.example.data.repository

import com.example.data.model.dto.NotificationDto
import com.example.data.model.entity.NotificationEntity
import com.example.data.model.entity.UserEntity
import com.example.data.source.local.NotificationLocalDataSource
import com.example.data.source.local.UserLocalDataSource
import com.example.data.source.remote.NotificationRemoteDataSource
import com.example.domain.model.NotificationType
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

private class FakeNotificationRemoteDataSource : NotificationRemoteDataSource {
    var notificationsResult: Result<List<NotificationDto>> = Result.success(emptyList())
    var deleteResult: Result<Unit> = Result.success(Unit)
    val deletedIds = mutableListOf<String>()

    override suspend fun getNotificationsForUser(userId: String): Result<List<NotificationDto>> = notificationsResult

    override suspend fun deleteNotification(notificationId: String): Result<Unit> {
        deletedIds.add(notificationId)
        return deleteResult
    }
}

private class FakeNotificationLocalDataSource : NotificationLocalDataSource {
    val saved = mutableListOf<NotificationEntity>()
    val deleted = mutableListOf<String>()

    override suspend fun saveNotifications(notifications: List<NotificationEntity>) {
        saved.addAll(notifications)
    }

    override suspend fun getNotificationsByUserId(userId: String): List<NotificationEntity> =
        saved.filter { it.userId == userId }

    override suspend fun deleteNotification(notificationId: String) {
        deleted.add(notificationId)
    }
}

private class FakeUserLocalDataSource(private val authUserId: String? = "uid") : UserLocalDataSource {
    override suspend fun saveUser(user: UserEntity) {}
    override suspend fun getUserById(userId: String): UserEntity? = null
    override suspend fun deleteUser(userId: String) {}
    override suspend fun getAuthUser(): UserEntity? =
        authUserId?.let { UserEntity(it, "", "", "", null, "") }
}

class NotificationRepositoryImplTest {
    @Test
    fun getAllNotifications_success_savesLocalAndReturnsDomain() = runBlocking {
        val remote = FakeNotificationRemoteDataSource().apply {
            notificationsResult = Result.success(
                listOf(
                    NotificationDto("1", "uid", "title", "body", NotificationType.SYSTEM, 100L)
                )
            )
        }
        val local = FakeNotificationLocalDataSource()
        val repo = NotificationRepositoryImpl(remote, local, FakeUserLocalDataSource("uid"))
        val result = repo.getAllNotifications()
        assertTrue(result.isSuccess)
        assertEquals(1, local.saved.size)
        assertEquals("1", result.getOrNull()?.first()?.id)
    }

    @Test
    fun getAllNotifications_failure_propagatesError() = runBlocking {
        val remote = FakeNotificationRemoteDataSource().apply {
            notificationsResult = Result.failure(Exception("network"))
        }
        val local = FakeNotificationLocalDataSource()
        val repo = NotificationRepositoryImpl(remote, local, FakeUserLocalDataSource("uid"))
        val result = repo.getAllNotifications()
        assertTrue(result.isFailure)
        assertTrue(local.saved.isEmpty())
    }

    @Test
    fun deleteNotifications_success_deletesLocal() = runBlocking {
        val remote = FakeNotificationRemoteDataSource()
        val local = FakeNotificationLocalDataSource()
        val repo = NotificationRepositoryImpl(remote, local, FakeUserLocalDataSource("uid"))
        val result = repo.deleteNotifications(listOf("a", "b"))
        assertTrue(result.isSuccess)
        assertEquals(listOf("a", "b"), remote.deletedIds)
        assertEquals(listOf("a", "b"), local.deleted)
    }

    @Test
    fun deleteNotifications_failure_stopsAndReturnsError() = runBlocking {
        val remote = FakeNotificationRemoteDataSource().apply {
            deleteResult = Result.failure(Exception("fail"))
        }
        val local = FakeNotificationLocalDataSource()
        val repo = NotificationRepositoryImpl(remote, local, FakeUserLocalDataSource("uid"))
        val result = repo.deleteNotifications(listOf("a"))
        assertTrue(result.isFailure)
        assertTrue(local.deleted.isEmpty())
    }

    @Test
    fun markNotificationAsRead_success_deletesRemoteAndLocal() = runBlocking {
        val remote = FakeNotificationRemoteDataSource()
        val local = FakeNotificationLocalDataSource()
        val repo = NotificationRepositoryImpl(remote, local, FakeUserLocalDataSource("uid"))
        val result = repo.markNotificationAsRead("n1")
        assertTrue(result.isSuccess)
        assertEquals(listOf("n1"), remote.deletedIds)
        assertEquals(listOf("n1"), local.deleted)
    }

    @Test
    fun markNotificationAsRead_failure_keepsLocal() = runBlocking {
        val remote = FakeNotificationRemoteDataSource().apply {
            deleteResult = Result.failure(Exception("fail"))
        }
        val local = FakeNotificationLocalDataSource()
        val repo = NotificationRepositoryImpl(remote, local, FakeUserLocalDataSource("uid"))
        val result = repo.markNotificationAsRead("n1")
        assertTrue(result.isFailure)
        assertTrue(local.deleted.isEmpty())
    }

    @Test
    fun getUnreadNotifications_success_returnsData() = runBlocking {
        val remote = FakeNotificationRemoteDataSource().apply {
            notificationsResult = Result.success(
                listOf(
                    NotificationDto("1", "uid", "title", "body", NotificationType.SYSTEM, 100L)
                )
            )
        }
        val local = FakeNotificationLocalDataSource()
        val repo = NotificationRepositoryImpl(remote, local, FakeUserLocalDataSource("uid"))
        val result = repo.getUnreadNotifications()
        assertTrue(result.isSuccess)
        assertEquals("1", result.getOrNull()?.first()?.id)
    }

    @Test
    fun getUnreadNotifications_failure_propagates() = runBlocking {
        val remote = FakeNotificationRemoteDataSource().apply {
            notificationsResult = Result.failure(Exception("network"))
        }
        val local = FakeNotificationLocalDataSource()
        val repo = NotificationRepositoryImpl(remote, local, FakeUserLocalDataSource("uid"))
        val result = repo.getUnreadNotifications()
        assertTrue(result.isFailure)
    }
}
