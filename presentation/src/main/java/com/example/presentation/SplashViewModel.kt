package com.example.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.UserRole
import com.example.domain.usecase.auth.GetAuthStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAuthStatusUseCase: GetAuthStatusUseCase
) : ViewModel() {

    val uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)

    init {
        viewModelScope.launch {
            runCatching {
                getAuthStatusUseCase()
            }.onSuccess { status ->
                Log.d("cursive", status.toString())
                uiState.value = when {
                    !status.isLoggedIn -> SplashUiState.GoToLogin
                    status.role == UserRole.ADMIN -> SplashUiState.GoToAdmin
                    status.role == UserRole.MEMBER -> SplashUiState.GoToMember
                    else -> SplashUiState.GoToLogin // GUEST or unknown
                }
            }.onFailure {
                uiState.value = SplashUiState.Error
            }
        }
    }
}

sealed class SplashUiState {
    object Loading : SplashUiState()
    object GoToAdmin : SplashUiState()
    object GoToMember : SplashUiState()
    object GoToLogin : SplashUiState()
    object Error : SplashUiState()
}