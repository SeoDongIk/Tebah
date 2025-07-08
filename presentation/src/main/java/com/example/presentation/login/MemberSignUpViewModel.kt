package com.example.presentation.login

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import com.example.presentation.model.SignUpSideEffect
import com.example.presentation.model.SignUpState
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
class MemberSignUpViewModel @Inject constructor(
//    private val userSignUpUseCase: UserSignUpUseCase
) : ViewModel(), SignUpViewModel<UserSignUpState> {

    override val container: Container<UserSignUpState, SignUpSideEffect> = container(
        initialState = UserSignUpState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent { postSideEffect(SignUpSideEffect.Toast(throwable.message.orEmpty())) }
            }
        }
    )

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
    fun onChurchSelect(churchId: String) = blockingIntent {
        reduce { state.copy(selectedChurchId = churchId) }
    }

    override fun onSignUpClick() {
        intent {
            if(state.password != state.repeatPassword){
                postSideEffect(SignUpSideEffect.Toast("비밀번호가 일치하지 않습니다."))
                return@intent
            }
            val isSuccessful = true

            if(isSuccessful){
                postSideEffect(SignUpSideEffect.NavigateToMainActivity(false))
                postSideEffect(SignUpSideEffect.Toast("회원가입 성공"))
            }
        }
    }
}

@Immutable
data class UserSignUpState(
    override val id: String = "",
    override val password: String = "",
    override val repeatPassword: String = "",
    val name: String = "",
    val selectedChurchId: String? = null
) : SignUpState