package com.example.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.model.MemberSignUpRequest
import com.example.domain.model.UserRole
import com.example.domain.usecase.auth.SignInUseCase
import com.example.domain.usecase.auth.SignUpMemberUseCase
import com.example.presentation.auth.state.UserSignUpState
import com.example.presentation.auth.state.SignUpSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MemberSignUpViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val userSignUpUseCase: SignUpMemberUseCase
) : ViewModel(), SignUpViewModel<UserSignUpState> {

    override val container: Container<UserSignUpState, SignUpSideEffect> = container(
        initialState = UserSignUpState(
            id = TODO(),
            password = TODO(),
            repeatPassword = TODO(),
            name = TODO(),
            selectedRegion = TODO(),
            churchesByRegion = TODO(),
            selectedChurchId = TODO(),
            isPasswordMismatchDialogShown = TODO(),
            isIdDuplicateDialogShown = TODO(),
            isSigningUp = TODO(),
            isSigningIn = TODO()
        ),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
//                intent { postSideEffect(SignUpSideEffect.Toast(throwable.message.orEmpty())) }
            }
        }
    )

    init {
        intent {
            reduce {
                state.copy(
                    churchesByRegion = mapOf(
                        "전체" to listOf("은혜교회", "사랑교회", "하나교회", "기쁨교회"),
                        "서울" to listOf("서울제일교회", "강남은혜교회"),
                        "인천/경기" to listOf("수원교회", "인천믿음교회"),
                        "대전/충남" to listOf("대전중앙교회"),
                        "광주/전남" to listOf("광주은혜교회"),
                        "대구/경북" to listOf("대구제일교회"),
                        "부산/경남" to listOf("부산영광교회")
                    ),
                    selectedRegion = "전체"
                )
            }
        }
    }

    fun onNameChange(name: String) = blockingIntent {
        reduce { state.copy(name = name) }
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

    fun onRegionSelected(region: String) = blockingIntent {
        reduce { state.copy(selectedRegion = region, selectedChurchId = null) }
    }

    fun onChurchSelected(churchId: String) = blockingIntent {
        reduce { state.copy(selectedChurchId = churchId) }
    }

    val isNextEnabled: Boolean
        get() = with(container.stateFlow.value) {
            name.isNotBlank() &&
                    id.isNotBlank() &&
                    password.isNotBlank() &&
                    repeatPassword.isNotBlank()
        }

    fun onNextClicked() = intent {
        val s = state
        if (s.password != s.repeatPassword) {
            reduce { s.copy(isPasswordMismatchDialogShown = true) }
            return@intent
        }
        // TODO: 아이디 중복 체크 가능

        postSideEffect(SignUpSideEffect.NavigateToChurchSelect)
    }

    fun dismissPasswordMismatchDialog() = intent {
        reduce { state.copy(isPasswordMismatchDialogShown = false) }
    }

    fun dismissIdDuplicateDialog() = intent {
        reduce { state.copy(isIdDuplicateDialogShown = false) }
    }

    override fun onSignInClick() {
        intent {
            try {
                val result = signInUseCase(state.id, state.password, true)
                result.onSuccess {
                    postSideEffect(SignUpSideEffect.NavigateToMain(UserRole.MEMBER)) // 멤버니까 false
//                    postSideEffect(SignUpSideEffect.Toast("로그인 성공"))
                }.onFailure { e ->
//                    postSideEffect(SignUpSideEffect.Toast(e.message ?: "로그인 실패"))
                }
            } catch (e: Exception) {
//                postSideEffect(SignUpSideEffect.Toast(e.message ?: "로그인 실패"))
            }
        }
    }

    override fun onSignUpClick() {
        intent {
            if (state.name.isBlank() || state.id.isBlank() || state.password.isBlank() || state.repeatPassword.isBlank() || state.selectedChurchId.isNullOrBlank()) {
//                postSideEffect(SignUpSideEffect.Toast("모든 정보를 정확히 입력해주세요."))
                return@intent
            }
            if (state.password != state.repeatPassword) {
                reduce { state.copy(isPasswordMismatchDialogShown = true) }
                return@intent
            }

            val request = MemberSignUpRequest(
                email = state.id,
                password = state.password,
                name = state.name,
                churchId = state.selectedChurchId!!
            )

            try {
                val result = userSignUpUseCase(request)
                result.onSuccess {
//                    postSideEffect(SignUpSideEffect.Toast("회원가입 성공"))
                    postSideEffect(SignUpSideEffect.NavigateToCompleteScreen)
                }.onFailure { e ->
//                    postSideEffect(SignUpSideEffect.Toast(e.message ?: "회원가입 실패"))
                }
            } catch (e: Exception) {
//                postSideEffect(SignUpSideEffect.Toast(e.message ?: "회원가입 실패"))
            }
        }
    }
}