package com.example.presentation.auth.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.domain.model.AdminPosition
import com.example.domain.model.ChurchInfo
import com.example.domain.model.Region
import com.example.domain.usecase.auth.SignInUseCase
import com.example.domain.usecase.auth.SignUpAdminUseCase
import com.example.presentation.R
import com.example.presentation.auth.state.AdminSignUpState
import com.example.presentation.auth.state.SignUpSideEffect
import com.example.presentation.common.state.MediumDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.sync.Mutex
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
                intent {
                    postSideEffect(SignUpSideEffect.Toast(R.string.error_unknown))
                }
            }
        }
    )

    /* ---------------- Validation helpers ---------------- */

    private companion object {
        private const val MIN_PASSWORD = 6
        private val PHONE_REGEX = Regex("^\\d{10,11}$")
    }

    private fun validateAdminName(name: String) = if (name.isBlank())
        R.string.error_empty_admin_name else null

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

    private fun validateCustomRole(isCustom: Boolean, value: String) =
        if (isCustom && value.isBlank()) R.string.error_empty_custom_role else null

    private fun validateChurchName(name: String) =
        if (name.isBlank()) R.string.error_empty_church_name else null

    private fun validatePhone(phone: String) = when {
        phone.isBlank() -> R.string.error_empty_phone
        !PHONE_REGEX.matches(phone) -> R.string.error_invalid_phone
        else -> null
    }

    private fun validateChurchIntro(intro: String) =
        if (intro.isBlank()) R.string.error_empty_church_intro else null

    private fun validateRegion(region: Region?) =
        if (region == null) R.string.error_empty_region else null

    /* ---------------- onChange: 즉시 검증 ---------------- */

    fun onChurchNameChange(name: String) = blockingIntent {
        reduce { state.copy(churchName = name, churchNameError = validateChurchName(name)) }
    }

    fun onRegionChange(region: Region) = intent {
        reduce { state.copy(region = region, regionError = null) }
    }

    fun onPhoneNumberChange(phone: String) = blockingIntent {
        reduce { state.copy(phoneNumber = phone, phoneNumberError = validatePhone(phone)) }
    }

    fun onChurchIntroChange(intro: String) = blockingIntent {
        reduce { state.copy(churchIntro = intro, churchIntroError = validateChurchIntro(intro)) }
    }

    fun onAdminNameChange(name: String) = blockingIntent {
        reduce { state.copy(name = name, nameError = validateAdminName(name)) }
    }

    fun onIdChange(id: String) = blockingIntent {
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

    fun onAdminRoleChange(role: AdminPosition) = intent {
        reduce {
            if (role != AdminPosition.CUSTOM) {
                state.copy(adminRole = role, customRole = "", customRoleError = null)
            } else {
                state.copy(
                    adminRole = role,
                    customRoleError = validateCustomRole(true, state.customRole)
                )
            }
        }
    }

    fun onCustomRoleChange(customRole: String) = intent {
        reduce {
            state.copy(
                customRole = customRole,
                customRoleError = validateCustomRole(state.isCustomInput, customRole)
            )
        }
    }

    /* ---------------- Dialog & Selector ---------------- */

    fun showRegionSelector() = intent {
        reduce { state.copy(regionSelectorVisible = true) }
    }

    fun dismissRegionSelector() = intent {
        reduce { state.copy(regionSelectorVisible = false) }
    }

    private fun showErrorDialog(@androidx.annotation.StringRes msg: Int) = intent {
        reduce {
            state.copy(
                dialog = MediumDialogState(
                    titleRes = R.string.error_title,
                    messageRes = msg,
                    confirmTextRes = R.string.confirm_button_text
                )
            )
        }
    }

    fun dismissDialog() = intent {
        reduce { state.copy(dialog = null) }
    }

    /* ---------------- 제출 로직 ---------------- */

    private val signUpMutex = Mutex()
    private val signInMutex = Mutex()

    // 로그인
    override fun onSignInClick() {
        intent {
            if (state.isSigningIn) return@intent
            if (!signInMutex.tryLock()) return@intent
            try {
                val s = state

                // 검증 반영
                val idErr = validateEmail(s.id)
                val pwErr = validatePassword(s.password)
                reduce { state.copy(idError = idErr, passwordError = pwErr) }
                if (idErr != null || pwErr != null) {
                    showErrorDialog(R.string.invalid_user_info)
                    return@intent
                }

                // 로딩 시작
                reduce { state.copy(isSigningIn = true) }

                // 3) 호출 (Result 직접 처리)
                try {
                    val result = signInUseCase(s.id, s.password, true)
                    result
                        .onSuccess {
                            // 성공 UI/네비 처리: 필요 시 sideEffect로 변경 가능
                            showErrorDialog(R.string.login_success)
                        }
                        .onFailure { showErrorDialog(R.string.login_failed) }
                } catch (_: Throwable) {
                    showErrorDialog(R.string.login_failed)
                } finally {
                    reduce { state.copy(isSigningIn = false) }
                }
            } finally {
                signInMutex.unlock()
            }
        }
    }

    // 회원가입
    override fun onSignUpClick() {
        intent {
            if (state.isSigningUp) return@intent
            if (!signUpMutex.tryLock()) return@intent
            try {
                val s = state // 스냅샷

                // 에러 일괄 계산
                val userErr = s.copy(
                    nameError = validateAdminName(s.name),
                    idError = validateEmail(s.id),
                    passwordError = validatePassword(s.password),
                    repeatPasswordError = validateRepeatPassword(s.password, s.repeatPassword),
                    customRoleError = validateCustomRole(s.isCustomInput, s.customRole)
                )
                val allErr = userErr.copy(
                    churchNameError = validateChurchName(userErr.churchName),
                    phoneNumberError = validatePhone(userErr.phoneNumber),
                    churchIntroError = validateChurchIntro(userErr.churchIntro),
                    regionError = validateRegion(userErr.region)
                )
                reduce { allErr }

                // 가드
                val invalidUser = listOf(
                    allErr.nameError, allErr.idError, allErr.passwordError,
                    allErr.repeatPasswordError, allErr.customRoleError
                ).any { it != null }
                val invalidChurch = listOf(
                    allErr.churchNameError, allErr.phoneNumberError,
                    allErr.churchIntroError, allErr.regionError
                ).any { it != null }

                if (invalidUser) { showErrorDialog(R.string.invalid_user_info); return@intent }
                if (invalidChurch) { showErrorDialog(R.string.invalid_church_info); return@intent }

                // 로딩 시작
                reduce { state.copy(isSigningUp = true) }

                // 요청 빌드 (스냅샷 기준, region!! 안전)
                val request = SignUpAdminUseCase.AdminSignUpRequest(
                    church = ChurchInfo(
                        name = s.churchName,
                        region = s.region!!,
                        phone = s.phoneNumber,
                        description = s.churchIntro
                    ),
                    adminName = s.name,
                    adminEmail = s.id,
                    adminPassword = s.password,
                    adminPosition = s.adminRole,
                    customPosition = if (s.adminRole == AdminPosition.CUSTOM) s.customRole else null
                )

                Timber.d("🚀 [AdminSignUp] request: $request")

                // 호출 (Result 직접 처리)
                try {
                    val result = signUpAdminUseCase(request)
                    result
                        .onSuccess {
                            postSideEffect(SignUpSideEffect.NavigateToCompleteScreen)
                        }
                        .onFailure { showErrorDialog(R.string.signup_failed) }
                } catch (_: Throwable) {
                    showErrorDialog(R.string.signup_failed)
                } finally {
                    reduce { state.copy(isSigningUp = false) }
                }
            } finally {
                signUpMutex.unlock()
            }
        }
    }
}