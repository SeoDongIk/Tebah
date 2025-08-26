package com.example.data.source.local.datastore

import androidx.datastore.core.DataStore
import com.example.data.ApprovalProto
import com.example.data.UserPreferences
import com.example.data.UserRoleProto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

class DefaultAppPreferencesDataStore @Inject constructor(
    private val dataStore: DataStore<UserPreferences>
) : AppPreferencesDataStore {

    private val TAG = "AppPreferencesDataStore"

    override val userId: Flow<String?> = dataStore.data
        .map { it.userId.ifBlank { null } }
        .logDistinct("userId")

    override val isAutoLogin: Flow<Boolean> = dataStore.data
        .map { it.isAutoLogin }
        .logDistinct("isAutoLogin")

    override val userRole: Flow<UserRoleProto> = dataStore.data
        .map { it.userRole }
        .logDistinct("userRole")

    override val approval: Flow<ApprovalProto> = dataStore.data
        .map { it.approval }
        .logDistinct("approval")

    override val lastSyncedAt: Flow<Long?> = dataStore.data
        .map { prefs -> prefs.lastSyncedAtEpochMillis.takeIf { it > 0 } }
        .logDistinct("lastSyncedAt")

    override suspend fun updateUserInfo(
        id: String,
        isAutoLogin: Boolean,
        role: UserRoleProto,
        approval: ApprovalProto?,
        lastSyncedAt: Long?
    ) {
        Timber.tag(TAG).d("updateUserInfo() called with id=$id, isAutoLogin=$isAutoLogin, role=$role, approval=$approval, lastSyncedAt=$lastSyncedAt")
        dataStore.updateData { current ->
            current.toBuilder()
                .setUserId(id)
                .setIsAutoLogin(isAutoLogin)
                .setUserRole(role)
                .apply { approval?.let { setApproval(it) } }
                .apply { lastSyncedAt?.let { setLastSyncedAtEpochMillis(it) } }
                .build()
        }
    }

    override suspend fun clear() {
        Timber.tag(TAG).d("clear() called")
        dataStore.updateData {
            it.toBuilder()
                .clearUserId()
                .clearIsAutoLogin()
                .clearUserRole()
                .clearApproval()
                .clearLastSyncedAtEpochMillis()
                .build()
        }
    }

    private fun <T> Flow<T>.logDistinct(name: String): Flow<T> =
        distinctUntilChanged().onEach { Timber.tag(TAG).d("$name: $it") }
}