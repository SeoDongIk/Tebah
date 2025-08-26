package com.example.data.source.local.datastore

import com.example.data.ApprovalProto
import com.example.data.UserRoleProto
import kotlinx.coroutines.flow.Flow

interface AppPreferencesDataStore {
    val userId: Flow<String?>
    val isAutoLogin: Flow<Boolean>
    val userRole: Flow<UserRoleProto>
    val approval: Flow<ApprovalProto>
    val lastSyncedAt: Flow<Long?>

    suspend fun updateUserInfo(
        id: String,
        isAutoLogin: Boolean,
        role: UserRoleProto,
        approval: ApprovalProto? = null,
        lastSyncedAt: Long? = null
    )
    suspend fun clear()
}