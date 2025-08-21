package com.example.presentation.auth.viewmodel

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

    fun onIdChange(id: String) = intent {
        val errorRes = when {
            id.isBlank() -> R.string.error_id_required
            !android.util.Patterns.EMAIL_ADDRESS.matcher(id).matches() -> R.string.error_invalid_email
            else -> null
        }
        reduce {
            state.copy(
                id = id,
                idError = errorRes,
                isLoginEnabled = validate(id, state.password)
            )
        }
    }

    fun onPasswordChange(password: String) = intent {
        val errorRes = when {
            password.isBlank() -> R.string.error_password_required
            password.length < 6 -> R.string.error_password_too_short
            else -> null
        }
        reduce {
            state.copy(
                password = password,
                passwordError = errorRes,
                isLoginEnabled = validate(state.id, password)
            )
        }
    }

    private fun validate(id: String, password: String): Boolean {
        return id.isNotBlank()
                && password.length >= 6
                && android.util.Patterns.EMAIL_ADDRESS.matcher(id).matches()
    }

    fun onAutoLoginChange(autoLogin: Boolean) = intent {
        reduce { state.copy(autoLogin = autoLogin) }
    }

    fun onSignInClick() = intent {
        reduce { state.copy(isLoading = true) }
        signInUseCase(state.id, state.password, state.autoLogin)
            .onSuccess { result ->
                postSideEffect(SignInSideEffect.NavigateToMainActivity(result.role))
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
        reduce { state.copy(isLoading = false) }
    }

    fun onDismissDialog() = intent {
        reduce { state.copy(dialog = null) }
    }
}
