package com.example.presentation.login

import androidx.lifecycle.ViewModel
import com.example.domain.model.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
//    private val loginUseCase: LoginUseCase,
//    private val setTokenUseCase:SetTokenUseCase,
) : ViewModel(), ContainerHost<LoginState, LoginSideEffect> {

    override val container: Container<LoginState, LoginSideEffect> = container(
        initialState = LoginState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    postSideEffect(LoginSideEffect.Toast(message = throwable.message.orEmpty()))
                }
            }
        }
    )

    fun onLoginClick() = intent {
        val id = state.id
        val password = state.password
        val loginResult = LoginResult(token = "dfdf", isAdmin = false)
        val isAdmin = loginResult.isAdmin
//        setTokenUseCase(loginResult.token)
        reduce {
            state.copy(isAdmin = isAdmin)
        }
        postSideEffect(LoginSideEffect.NavigateToMainActivity(isAdmin))
    }

    fun onIdChange(id: String) = blockingIntent {
        reduce {
            state.copy(id = id)
        }
    }

    fun onPasswordChange(password: String) = blockingIntent {
        reduce {
            state.copy(password = password)
        }
    }
}

@Immutable
data class LoginState(
    val id: String = "",
    val password: String = "",
    val isAdmin: Boolean? = null
)

sealed interface LoginSideEffect {
    class Toast(val message: String) : LoginSideEffect
    data class NavigateToMainActivity(val isAdmin: Boolean) : LoginSideEffect
}