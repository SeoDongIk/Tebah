package com.example.domain.usecase.auth

import com.example.domain.model.ApprovalStatus
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

        // 디버깅 로그 추가
        println("🔍 Splash Debug - Local snapshot: $local")
        println("🔍 Splash Debug - Session: ${local.session}")
        println("🔍 Splash Debug - AutoLogin: ${local.autoLogin}")
        println("🔍 Splash Debug - Role: ${local.role}")
        println("🔍 Splash Debug - Approval: ${local.approval}")

        // 첫 설치인지 확인 (모든 데이터가 기본값인 경우)
        val isFirstInstall = isFirstInstallation(local)
        println("🔍 Splash Debug - IsFirstInstall: $isFirstInstall")
        
        if (isFirstInstall) {
            println("🔍 Splash Debug - Returning Login (first install)")
            return SplashDestination.Login
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
    
    private fun isFirstInstallation(snapshot: com.example.domain.model.AuthSnapshot): Boolean {
        // 더 엄격한 첫 설치 체크
        val hasNoLocalData = !snapshot.autoLogin &&
                           snapshot.role == RoleStatus.UNKNOWN &&
                           snapshot.approval == ApprovalStatus.UNKNOWN &&
                           snapshot.lastSyncedAt == null
        
        // Firebase Auth가 자동 로그인되어 있어도 로컬 데이터가 없으면 첫 설치로 간주
        val isFirstInstall = hasNoLocalData
        
        println("🔍 Splash Debug - HasNoLocalData: $hasNoLocalData")
        println("🔍 Splash Debug - IsFirstInstall: $isFirstInstall")
        
        return isFirstInstall
    }
}