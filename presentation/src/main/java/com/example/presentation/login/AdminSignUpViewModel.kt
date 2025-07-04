package com.example.presentation.login

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
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
class AdminSignUpViewModel @Inject constructor(
//    private val adminSignUpUseCase: AdminSignUpUseCase
) : ViewModel(), SignUpViewModel<AdminSignUpState> {

    override val container: Container<AdminSignUpState, SignUpSideEffect> = container(
        initialState = AdminSignUpState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent { postSideEffect(SignUpSideEffect.Toast(throwable.message.orEmpty())) }
            }
        }
    )

    // 필드별 onChange 함수들
    fun onChurchNameChange(name: String) = blockingIntent {
        reduce { state.copy(churchName = name) }
    }
    fun onRegionChange(region: String) = blockingIntent {
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
    fun onAdminRoleChange(role: String) = blockingIntent {
        reduce { state.copy(adminRole = role) }
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

    override fun onSignUpClick() {
        intent {
            if(state.password != state.repeatPassword){
                postSideEffect(SignUpSideEffect.Toast("비밀번호가 일치하지 않습니다."))
                return@intent
            }
            val isSuccessful = true

            if(isSuccessful){
                postSideEffect(SignUpSideEffect.NavigateToMainActivity(true))
                postSideEffect(SignUpSideEffect.Toast("관리자 회원가입 성공"))
            }
        }
    }
}

@Immutable
data class AdminSignUpState(
    override val id: String = "",
    override val password: String = "",
    override val repeatPassword: String = "",
    val churchName: String = "",
    val region: String = "",
    val phoneNumber: String = "",
    val churchIntro: String = "",
    val adminName: String = "",
    val adminRole: String = "목사"
) : SignUpState