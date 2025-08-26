package com.example.domain.model

sealed class SessionStatus {
    data object Anonymous : SessionStatus()
    data class LoggedIn(val uid: String, val tokenExpiry: Long? = null) : SessionStatus()
    sealed class Failure : SessionStatus() {
        data object NoSession : Failure()
        data object TokenExpired : Failure()
        data class Network(val cause: Throwable? = null) : Failure()
        data class Unknown(val cause: Throwable? = null) : Failure()
    }
}

enum class RoleStatus { ADMIN, MEMBER, GUEST, UNKNOWN }
enum class ApprovalStatus { APPROVED, PENDING, REJECTED, UNKNOWN }

data class AuthSnapshot(
    val session: SessionStatus,
    val role: RoleStatus,
    val approval: ApprovalStatus,
    val autoLogin: Boolean,
    val lastSyncedAt: Long?
)
