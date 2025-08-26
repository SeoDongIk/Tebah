package com.example.domain.usecase.auth

import com.example.domain.model.ApprovalStatus
import com.example.domain.model.AuthSnapshot
import com.example.domain.model.RoleStatus
import com.example.domain.model.SessionStatus
import com.example.domain.model.SplashDestination
import com.example.domain.repository.AuthSessionRepository
import javax.inject.Inject

class DecideSplashDestinationUseCase @Inject constructor(
    private val repository: AuthSessionRepository
) {
    suspend operator fun invoke(): SplashDestination {
        val local = repository.getSnapshot().getOrElse {
            return SplashDestination.Error("snapshot-fail")
        }

        val snap = if (local.session is SessionStatus.LoggedIn || local.autoLogin) {
            repository.refreshFromServer().getOrElse { local }
        } else {
            local
        }

        return when (snap.session) {
            is SessionStatus.LoggedIn -> when (snap.approval) {
                ApprovalStatus.PENDING -> SplashDestination.PendingApproval
                ApprovalStatus.REJECTED -> SplashDestination.Login
                ApprovalStatus.APPROVED, ApprovalStatus.UNKNOWN -> when (snap.role) {
                    RoleStatus.ADMIN -> SplashDestination.AdminMain
                    RoleStatus.MEMBER -> SplashDestination.MemberMain
                    RoleStatus.GUEST, RoleStatus.UNKNOWN -> SplashDestination.Start
                }
            }
            is SessionStatus.Failure.NoSession -> if (snap.autoLogin) SplashDestination.Start else SplashDestination.Login
            is SessionStatus.Failure.TokenExpired -> SplashDestination.Login
            is SessionStatus.Failure.Network -> SplashDestination.Start
            is SessionStatus.Failure.Unknown, SessionStatus.Anonymous -> SplashDestination.Start
        }
    }
}
