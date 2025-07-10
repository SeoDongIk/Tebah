package com.example.presentation.login

import androidx.lifecycle.ViewModel
import com.example.domain.model.UserRole
import com.example.domain.usecase.auth.SignInUseCase
import com.example.presentation.model.LoginSideEffect
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
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : ViewModel(), ContainerHost<LoginState, LoginSideEffect> {

    override val container: Container<LoginState, LoginSideEffect> = container(
        initialState = LoginState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(LoginSideEffect.Toast(throwable.message.orEmpty()))
                }
            }
        }
    )

    fun onLoginClick() = intent {
        val (id, password) = state.id to state.password

        val signInResult = signInUseCase(id, password)
        signInResult.onSuccess { result ->
            val role = result.role

            reduce { state.copy(role = role) }
            postSideEffect(LoginSideEffect.NavigateToMainActivity(role))
        }.onFailure { e ->
            postSideEffect(LoginSideEffect.Toast(e.message ?: "로그인에 실패했습니다."))
        }
    }

    fun onIdChange(id: String) = blockingIntent {
        reduce { state.copy(id = id) }
    }

    fun onPasswordChange(password: String) = blockingIntent {
        reduce { state.copy(password = password) }
    }
}

data class LoginState(
    val id: String = "",
    val password: String = "",
    val role: UserRole? = null
)