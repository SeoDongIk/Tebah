package com.example.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.auth.SignInUseCase
import com.example.presentation.auth.state.SignInState
import com.example.presentation.auth.state.SignInSideEffect
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
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(SignInSideEffect.Toast(throwable.message.orEmpty()))
                }
            }
        }
    )

    fun onSignInClick() = intent {
        signInUseCase(state.id, state.password, state.autoLogin).onSuccess { result ->
            reduce { state.copy(role = result.role) }
            postSideEffect(SignInSideEffect.NavigateToMainActivity(result.role))
        }.onFailure { e ->
            postSideEffect(SignInSideEffect.Toast(e.message ?: "로그인에 실패했습니다."))
        }
    }

    fun onIdChange(id: String) = blockingIntent {
        reduce { state.copy(id = id) }
    }

    fun onPasswordChange(password: String) = blockingIntent {
        reduce { state.copy(password = password) }
    }

    fun onAutoLoginChange(autoLogin: Boolean) = blockingIntent {
        reduce { state.copy(autoLogin = autoLogin) }
    }
}