package com.example.presentation.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.UserRole
import com.example.domain.usecase.auth.GetAuthStatusUseCase
import com.example.domain.usecase.auth.SignInAnonymouslyUseCase
import com.example.domain.usecase.network.CheckNetworkUseCase
import com.example.presentation.R
import com.example.presentation.splash.state.SplashUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkNetworkUseCase: CheckNetworkUseCase,
    private val getAuthStatusUseCase: GetAuthStatusUseCase,
    private val signInAnonymouslyUseCase: SignInAnonymouslyUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState: StateFlow<SplashUiState> = _uiState

    init {
        loadAuthStatus()
    }

    private fun loadAuthStatus() {
        viewModelScope.launch {
            _uiState.value = SplashUiState.Loading
            val startTime = System.currentTimeMillis()

            // 네트워크 연결 체크 먼저
            if (!checkNetworkUseCase()) {
                ensureMinDelay(startTime)
                _uiState.value = SplashUiState.Error(R.string.error_network)
                Timber.w("Network unavailable")
                return@launch
            }

            runCatching { getAuthStatusUseCase() }
                .onSuccess { status ->
                    ensureMinDelay(startTime)
                    _uiState.value = when {
                        status.isLoggedIn && status.role == UserRole.ADMIN -> SplashUiState.GoToAdmin
                        status.isLoggedIn && status.role == UserRole.MEMBER -> SplashUiState.GoToMember
                        !status.isLoggedIn -> SplashUiState.GoToLogin
                        else -> SplashUiState.GoToStart
                    }
                    Timber.i("Auth status loaded: $status")
                }
                .onFailure { e ->
                    ensureMinDelay(startTime)
                    _uiState.value = SplashUiState.Error(R.string.error_unknown)
                    Timber.e(e, "Failed to load auth status")
                }
        }
    }

    private suspend fun ensureMinDelay(startTime: Long, minDelay: Long = 500L) {
        val elapsed = System.currentTimeMillis() - startTime
        if (elapsed < minDelay) delay(minDelay - elapsed)
    }

    fun retry() {
        loadAuthStatus() // Loading 상태 세팅은 loadAuthStatus 내부에서 이미 처리
    }

    fun signInAsGuest(onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch {
            signInAnonymouslyUseCase()
                .onSuccess {
                    Timber.i("Guest login success")
                    onSuccess()
                }
                .onFailure { e ->
                    Timber.e(e, "Guest login failed")
                    onFailure()
                }
        }
    }
}