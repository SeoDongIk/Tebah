package com.example.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.model.AdminPosition
import com.example.domain.model.AdminSignUpRequest
import com.example.domain.model.ChurchInfo
import com.example.domain.model.Region
import com.example.domain.usecase.auth.SignInUseCase
import com.example.domain.usecase.auth.SignUpAdminUseCase
import com.example.presentation.auth.state.AdminSignUpState
import com.example.presentation.auth.state.SignUpSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AdminSignUpViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpAdminUseCase: SignUpAdminUseCase
) : ViewModel(), SignUpViewModel<AdminSignUpState> {

    override val container: Container<AdminSignUpState, SignUpSideEffect> = container(
        initialState = AdminSignUpState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent { postSideEffect(SignUpSideEffect.Toast(throwable.message.orEmpty())) }
            }
        }
    )

    fun onChurchNameChange(name: String) = blockingIntent {
        reduce { state.copy(churchName = name) }
    }
    fun onRegionChange(region: Region) = blockingIntent {
        reduce { state.copy(region = region) }
    }
    fun onPhoneNumberChange(phone: String) = blockingIntent {
        reduce { state.copy(phoneNumber = phone) }
    }
    fun onChurchIntroChange(intro: String) = blockingIntent {
        reduce { state.copy(churchIntro = intro) }
    }
    fun onAdminNameChange(name: String) = blockingIntent {
        reduce { state.copy(adminName = name) }
    }
    fun onIdChange(id: String) = blockingIntent {
        reduce { state.copy(id = id) }
    }
    fun onPasswordChange(password: String) = blockingIntent {
        reduce { state.copy(password = password) }
    }
    fun onRepeatPasswordChange(password: String) = blockingIntent {
        reduce { state.copy(repeatPassword = password) }
    }
    fun onAdminRoleChange(role: AdminPosition) = blockingIntent {
        reduce {
            if (role != AdminPosition.CUSTOM) {
                state.copy(adminRole = role, customRole = "")
            } else {
                state.copy(adminRole = role)
            }
        }
    }
    fun onCustomRoleChange(customRole: String) = blockingIntent {
        reduce { state.copy(customRole = customRole) }
    }

    override fun onSignInClick() {
        intent {
            try {
                val result = signInUseCase(state.id, state.password, true)
                result.onSuccess {
                    postSideEffect(SignUpSideEffect.NavigateToMainActivity(true)) // 관리자니까 true
                    postSideEffect(SignUpSideEffect.Toast("로그인 성공"))
                }.onFailure { e ->
                    postSideEffect(SignUpSideEffect.Toast(e.message ?: "로그인 실패"))
                }
            } catch (e: Exception) {
                postSideEffect(SignUpSideEffect.Toast(e.message ?: "로그인 실패"))
            }
        }
    }

    override fun onSignUpClick() {
        intent {
            if (!state.isUserInfoValid) {
                postSideEffect(SignUpSideEffect.Toast("입력 정보를 확인해주세요."))
                return@intent
            }

            val adminPosition = state.adminRole
            val customPosition = if (adminPosition == AdminPosition.CUSTOM) state.customRole else null

            val request = AdminSignUpRequest(
                church = ChurchInfo(
                    name = state.churchName,
                    region = state.region ?: Region.SEOUL,
                    phone = state.phoneNumber,
                    description = state.churchIntro
                ),
                adminName = state.adminName,
                adminEmail = state.id,
                adminPassword = state.password,
                adminPosition = adminPosition,
                customPosition = customPosition
            )

            try {
                Timber.d("🚀 [AdminSignUp] 회원가입 요청 시작: $request")
                val result = signUpAdminUseCase(request)
                result.onSuccess {
                    Timber.i("✅ [AdminSignUp] 회원가입 성공: $it")
                    postSideEffect(SignUpSideEffect.Toast("관리자 회원가입 성공"))
                    postSideEffect(SignUpSideEffect.NavigateToCompleteScreen)  // 네비게이션 이벤트로 변경
                }.onFailure { e ->
                    Timber.e(e, "❌ [AdminSignUp] 회원가입 실패: ${e.message}")
                    postSideEffect(SignUpSideEffect.Toast(e.message ?: "회원가입 실패"))
                }
            } catch (e: Exception) {
                Timber.e(e, "🔥 [AdminSignUp] 예외 발생: ${e.message}")
                postSideEffect(SignUpSideEffect.Toast(e.message ?: "회원가입 실패"))
            }
        }
    }
}