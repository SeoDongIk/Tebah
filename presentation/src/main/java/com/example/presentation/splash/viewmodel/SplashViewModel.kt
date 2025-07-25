package com.example.presentation.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.UserRole
import com.example.domain.usecase.auth.GetAuthStatusUseCase
import com.example.domain.usecase.auth.SignInAnonymouslyUseCase
import com.example.presentation.splash.state.SplashUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAuthStatusUseCase: GetAuthStatusUseCase,
    private val signInAnonymouslyUseCase: SignInAnonymouslyUseCase
) : ViewModel() {

    val uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)

    init {
        Timber.tag("SplashViewModel").d("init - start loading auth status")
        viewModelScope.launch {
            runCatching {
                Timber.tag("SplashViewModel").d("Calling getAuthStatusUseCase()")
                getAuthStatusUseCase()
            }.onSuccess { status ->
                Timber.tag("SplashViewModel").d("Calling getAuthStatusUseCase()")
                uiState.value = when {
                    status.isLoggedIn && status.role == UserRole.ADMIN -> SplashUiState.GoToAdmin
                    status.isLoggedIn && status.role == UserRole.MEMBER -> SplashUiState.GoToMember
                    !status.isLoggedIn && (status.role == UserRole.ADMIN || status.role == UserRole.MEMBER) -> SplashUiState.GoToLogin
                    else -> SplashUiState.GoToStart
                }
                Timber.tag("SplashViewModel").d("uiState updated: ${uiState.value}")
            }.onFailure { error ->
                Timber.tag("SplashViewModel").e(error, "getAuthStatusUseCase() failed")
                uiState.value = SplashUiState.Error
            }
        }
    }

    fun signInAsGuest(
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch {
            signInAnonymouslyUseCase()
                .onSuccess {
                    Timber.d("익명 로그인 성공")
                    onSuccess()
                }.onFailure {
                    Timber.e(it, "익명 로그인 실패")
                    onFailure()
                }
        }
    }
}