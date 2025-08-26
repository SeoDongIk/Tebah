package com.example.presentation.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.auth.DecideSplashDestinationUseCase
import com.example.domain.usecase.auth.SignInAnonymouslyUseCase
import com.example.presentation.R
import com.example.presentation.splash.state.SplashUiState
import com.example.presentation.splash.state.SplashNavEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val decideSplashDestinationUseCase: DecideSplashDestinationUseCase,
    private val signInAnonymouslyUseCase: SignInAnonymouslyUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState: StateFlow<SplashUiState> = _uiState

    private val _events = MutableSharedFlow<SplashNavEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<SplashNavEvent> = _events

    init {
        loadAuthStatus()
    }

    private fun loadAuthStatus() {
        viewModelScope.launch {
            _uiState.value = SplashUiState.Loading
            val startTime = System.currentTimeMillis()

            runCatching { decideSplashDestinationUseCase() }
                .onSuccess { dest ->
                    ensureMinDelay(startTime)
                    when (dest) {
                        com.example.domain.model.SplashDestination.AdminMain -> _events.tryEmit(SplashNavEvent.GoToAdmin)
                        com.example.domain.model.SplashDestination.MemberMain -> _events.tryEmit(SplashNavEvent.GoToMember)
                        com.example.domain.model.SplashDestination.PendingApproval -> _events.tryEmit(SplashNavEvent.GoToPending)
                        com.example.domain.model.SplashDestination.Login -> _events.tryEmit(SplashNavEvent.GoToLogin)
                        com.example.domain.model.SplashDestination.Start -> _uiState.value = SplashUiState.Start
                        is com.example.domain.model.SplashDestination.Error -> _uiState.value = SplashUiState.Error(R.string.error_unknown)
                    }
                    Timber.i("Destination decided: $dest")
                }
                .onFailure { e ->
                    ensureMinDelay(startTime)
                    _uiState.value = SplashUiState.Error(R.string.error_unknown)
                    Timber.e(e, "Failed to decide destination")
                }
        }
    }

    private suspend fun ensureMinDelay(startTime: Long, minDelay: Long = 500L) {
        val elapsed = System.currentTimeMillis() - startTime
        if (elapsed < minDelay) delay(minDelay - elapsed)
    }

    fun retry() {
        loadAuthStatus()
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