package com.example.presentation.auth.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.domain.model.MemberSignUpRequest
import com.example.domain.model.UserRole
import com.example.domain.usecase.auth.SignInUseCase
import com.example.domain.usecase.auth.SignUpMemberUseCase
import com.example.presentation.R
import com.example.presentation.auth.state.SignUpSideEffect
import com.example.presentation.auth.state.UserSignUpState
import com.example.presentation.common.state.MediumDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.sync.Mutex
import org.orbitmvi.orbit.Container
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
        initialState = UserSignUpState(),
        buildSettings = {
            exceptionHandler = CoroutineExceptionHandler { _, _ ->
                intent { postSideEffect(SignUpSideEffect.Toast(R.string.error_unknown)) }
            }
        }
    )

    /* ------------ 초기 데이터 세팅 (데모/임시) ------------ */
    init {
        intent {
            reduce {
                state.copy(
                    churchesByRegion = mapOf(
                        "전체"     to listOf("은혜교회", "사랑교회", "하나교회", "기쁨교회"),
                        "서울"     to listOf("서울제일교회", "강남은혜교회"),
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

    /* -------------------- Validators -------------------- */
    private companion object {
        private const val MIN_PASSWORD = 6
    }

    private fun validateName(name: String) =
        if (name.isBlank()) R.string.error_empty_admin_name else null // 공용 문자열 재사용

    private fun validateEmail(id: String) = when {
        id.isBlank() -> R.string.error_empty_id
        !Patterns.EMAIL_ADDRESS.matcher(id).matches() -> R.string.error_invalid_email
        else -> null
    }

    private fun validatePassword(pw: String) = when {
        pw.isBlank() -> R.string.error_empty_password
        pw.length < MIN_PASSWORD -> R.string.error_password_too_short
        else -> null
    }

    private fun validateRepeatPassword(pw: String, repeat: String) = when {
        repeat.isBlank() -> R.string.error_empty_password_confirm
        pw != repeat -> R.string.error_password_mismatch
        else -> null
    }

    /* -------------------- onChange ---------------------- */
    fun onNameChange(name: String) = intent {
        reduce { state.copy(name = name, nameError = validateName(name)) }
    }

    fun onIdChange(id: String) = intent {
        reduce { state.copy(id = id, idError = validateEmail(id)) }
    }

    fun onPasswordChange(password: String) = intent {
        reduce {
            state.copy(
                password = password,
                passwordError = validatePassword(password),
                repeatPasswordError = validateRepeatPassword(password, state.repeatPassword)
            )
        }
    }

    fun onRepeatPasswordChange(password: String) = intent {
        reduce {
            state.copy(
                repeatPassword = password,
                repeatPasswordError = validateRepeatPassword(state.password, password)
            )
        }
    }

    fun onRegionSelected(region: String) = intent {
        reduce { state.copy(selectedRegion = region, selectedChurchId = null) }
    }

    fun onChurchSelected(churchId: String) = intent {
        reduce { state.copy(selectedChurchId = churchId) }
    }

    /* ---------------- Dialog ---------------- */
    private fun showDialog(messageRes: Int) = intent {
        reduce {
            state.copy(
                dialog = MediumDialogState(
                    titleRes = R.string.error_title,
                    messageRes = messageRes,
                    confirmTextRes = R.string.confirm_button_text
                )
            )
        }
    }

    fun dismissDialog() = intent {
        reduce { state.copy(dialog = null) }
    }

    /* ----------------- Actions ------------------- */
    private val signUpMutex = Mutex()
    private val signInMutex = Mutex()

    // 로그인
    override fun onSignInClick() {
        intent {
            if (state.isSigningIn || !signInMutex.tryLock()) return@intent
            try {
                val idErr = validateEmail(state.id)
                val pwErr = validatePassword(state.password)
                reduce { state.copy(idError = idErr, passwordError = pwErr) }
                if (idErr != null || pwErr != null) {
                    showDialog(R.string.invalid_user_info)
                    return@intent
                }

                reduce { state.copy(isSigningIn = true) }
                runCatching { signInUseCase(state.id, state.password, true) }
                    .onSuccess {
                        postSideEffect(SignUpSideEffect.NavigateToMain(UserRole.MEMBER))
                    }
                    .onFailure {
                        showDialog(R.string.login_failed)
                    }
                reduce { state.copy(isSigningIn = false) }
            } finally {
                signInMutex.unlock()
            }
        }
    }

    // 회원가입 (교회 선택 화면의 "다음/가입" 버튼에서 호출)
    override fun onSignUpClick() {
        intent {
            if (state.isSigningUp || !signUpMutex.tryLock()) return@intent
            try {
                val s = state

                // 방어적 재검증
                val checked = s.copy(
                    nameError = validateName(s.name),
                    idError = validateEmail(s.id),
                    passwordError = validatePassword(s.password),
                    repeatPasswordError = validateRepeatPassword(s.password, s.repeatPassword)
                )
                reduce { checked }

                if (!checked.isMemberInfoValid) {
                    showDialog(R.string.invalid_user_info)
                    return@intent
                }
                if (!checked.isChurchSelectValid) {
                    showDialog(R.string.invalid_church_info)
                    return@intent
                }

                reduce { state.copy(isSigningUp = true) }

                val request = MemberSignUpRequest(
                    email = checked.id,
                    password = checked.password,
                    name = checked.name,
                    churchId = checked.selectedChurchId!!
                )

                runCatching { userSignUpUseCase(request) }
                    .onSuccess {
                        postSideEffect(SignUpSideEffect.NavigateToCompleteScreen)
                    }
                    .onFailure {
                        showDialog(R.string.signup_failed)
                    }

                reduce { state.copy(isSigningUp = false) }
            } finally {
                signUpMutex.unlock()
            }
        }
    }
}