package com.example.domain.usecase.auth

import com.example.domain.model.RootChannelSignUpRequest
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * 루트 채널을 새로 만들기 위한 가입을 요청하는 기능.
 * 승인 대기 상태로 계정이 생성 됩니다.
 */
class RequestRootChannelSignUpUseCase @Inject constructor(
    private val authRepsitory : AuthRepository
) {
    suspend operator fun invoke(request: RootChannelSignUpRequest): Result<Unit> {
        return authRepsitory.requestRootChannelSignUp(request)
    }
}