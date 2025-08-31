package com.example.presentation.auth.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.domain.usecase.auth.SignInUseCase
import com.example.presentation.R
import com.example.presentation.auth.state.SignInState
import com.example.presentation.auth.state.SignInSideEffect
import com.example.presentation.common.state.MediumDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : ViewModel(), ContainerHost<SignInState, SignInSideEffect> {

    override val container: Container<SignInState, SignInSideEffect> = container(
        initialState = SignInState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, _ ->
                intent {
                    postSideEffect(SignInSideEffect.Toast(R.string.error_unknown))
                }
            }
        }
    )

    private fun validateEmail(id: String): Int? = when {
        id.isBlank() -> R.string.error_id_required
        !Patterns.EMAIL_ADDRESS.matcher(id).matches() -> R.string.error_invalid_email
        else -> null
    }

    private fun validatePassword(password: String): Int? = when {
        password.isBlank() -> R.string.error_password_required
        password.length < 6 -> R.string.error_password_too_short
        else -> null
    }

    fun onIdChange(id: String) = blockingIntent {
        reduce {
            state.copy(
                id = id,
                idError = validateEmail(id) // 유효하면 null
            )
        }
    }

    fun onPasswordChange(password: String) = blockingIntent {
        reduce {
            state.copy(
                password = password,
                passwordError = validatePassword(password) // 유효하면 null
            )
        }
    }

    fun onAutoLoginChange(autoLogin: Boolean) = intent {
        reduce { state.copy(autoLogin = autoLogin) }
    }

    fun onSignInClick() = intent {
        // 1) 에러 먼저 최신화 (사용자가 변경 안 했더라도 버튼 누르면 에러 표출)
        val emailErr = validateEmail(state.id)
        val pwErr = validatePassword(state.password)
        reduce { state.copy(idError = emailErr, passwordError = pwErr) }

        // 2) 비활성/에러면 종료
        if (emailErr != null || pwErr != null || state.id.isBlank() || state.password.isBlank() || state.isLoading) {
            return@intent
        }

        // 3) 요청 + 모든 경로에서 로딩 해제 보장
        reduce { state.copy(isLoading = true) }
        try {
            signInUseCase(state.id, state.password, state.autoLogin)
                .onSuccess { result ->
                    postSideEffect(SignInSideEffect.NavigateToMainActivity(result))
                }
                .onFailure {
                    reduce {
                        state.copy(
                            dialog = MediumDialogState(
                                titleRes = R.string.dialog_signin_failed_title,
                                messageRes = R.string.dialog_signin_failed_message,
                                confirmTextRes = R.string.dialog_confirm
                            )
                        )
                    }
                }
        } finally {
            reduce { state.copy(isLoading = false) }
        }
    }

    fun onDismissDialog() = intent {
        reduce { state.copy(dialog = null) }
    }
}
