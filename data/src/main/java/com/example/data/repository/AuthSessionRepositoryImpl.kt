package com.example.data.repository

import com.example.data.ApprovalProto
import com.example.data.UserRoleProto
import com.example.data.source.local.datastore.DefaultAppPreferencesDataStore
import com.example.data.source.remote.AuthRemoteDataSource
import com.example.domain.model.ApprovalStatus
import com.example.domain.model.AuthSnapshot
import com.example.domain.model.RoleStatus
import com.example.domain.model.SessionStatus
import com.example.domain.repository.AuthSessionRepository
import com.example.domain.model.UserRole
import com.example.data.mapper.toProto
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull

class AuthSessionRepositoryImpl @Inject constructor(
    private val remote: AuthRemoteDataSource,
    private val prefs: DefaultAppPreferencesDataStore,
) : AuthSessionRepository {

    override suspend fun getSnapshot(): Result<AuthSnapshot> = runCatching {
        val auto = prefs.isAutoLogin.firstOrNull() ?: false
        val roleProto = prefs.userRole.firstOrNull() ?: UserRoleProto.GUEST
        val role = when (roleProto) {
            UserRoleProto.ADMIN -> RoleStatus.ADMIN
            UserRoleProto.MEMBER -> RoleStatus.MEMBER
            UserRoleProto.GUEST -> RoleStatus.GUEST
            else -> RoleStatus.UNKNOWN
        }
        val approvalProto = prefs.approval.firstOrNull() ?: ApprovalProto.APPROVAL_PROTO_UNSPECIFIED
        val approval = when (approvalProto) {
            ApprovalProto.APPROVED -> ApprovalStatus.APPROVED
            ApprovalProto.PENDING -> ApprovalStatus.PENDING
            ApprovalProto.REJECTED -> ApprovalStatus.REJECTED
            else -> ApprovalStatus.UNKNOWN
        }
        val uid = prefs.userId.firstOrNull()
        val session = if (remote.isLoggedIn() && uid != null) {
            SessionStatus.LoggedIn(uid)
        } else {
            SessionStatus.Failure.NoSession
        }
        AuthSnapshot(
            session = session,
            role = role,
            approval = approval,
            autoLogin = auto,
            lastSyncedAt = prefs.lastSyncedAt.firstOrNull()
        )
    }

    override suspend fun refreshFromServer(): Result<AuthSnapshot> = runCatching {
        val uid = prefs.userId.firstOrNull()
        val session = if (remote.isLoggedIn() && uid != null) {
            SessionStatus.LoggedIn(uid)
        } else {
            SessionStatus.Failure.NoSession
        }
        if (session is SessionStatus.LoggedIn) {
            val dto = remote.getUserById(session.uid) ?: error("User not found")
            val role = when (UserRole.fromString(dto.role)) {
                UserRole.ADMIN -> RoleStatus.ADMIN
                UserRole.MEMBER -> RoleStatus.MEMBER
                UserRole.GUEST -> RoleStatus.GUEST
            }
            val approval = when (dto.isApproved) {
                true -> ApprovalStatus.APPROVED
                false -> ApprovalStatus.PENDING
                null -> ApprovalStatus.UNKNOWN
            }
            val now = System.currentTimeMillis()
            prefs.updateUserInfo(
                id = dto.id,
                isAutoLogin = prefs.isAutoLogin.firstOrNull() ?: false,
                role = UserRole.fromString(dto.role).toProto(),
                approval = when (approval) {
                    ApprovalStatus.APPROVED -> ApprovalProto.APPROVED
                    ApprovalStatus.PENDING -> ApprovalProto.PENDING
                    ApprovalStatus.REJECTED -> ApprovalProto.REJECTED
                    ApprovalStatus.UNKNOWN -> ApprovalProto.APPROVAL_PROTO_UNSPECIFIED
                },
                lastSyncedAt = now
            )
            AuthSnapshot(session, role, approval, prefs.isAutoLogin.firstOrNull() ?: false, now)
        } else {
            AuthSnapshot(session, RoleStatus.UNKNOWN, ApprovalStatus.UNKNOWN, prefs.isAutoLogin.firstOrNull() ?: false, System.currentTimeMillis())
        }
    }

    override suspend fun signOut(): Result<Unit> = runCatching {
        remote.signOut().getOrThrow()
        prefs.clear()
    }
}
