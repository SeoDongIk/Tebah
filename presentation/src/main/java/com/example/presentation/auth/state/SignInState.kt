package com.example.presentation.auth.state

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import com.example.presentation.common.state.MediumDialogState

@Stable
data class SignInState(
    val id: String = "",
    val password: String = "",
    @StringRes val idError: Int? = null, // null 여부로 isError 여부 판단
    @StringRes val passwordError: Int? = null, // null 여부로 isError 여부 판단
    val autoLogin: Boolean = false,
    val isLoading: Boolean = false,
    val dialog: MediumDialogState? = null,
) {
    val isLoginEnabled: Boolean
        get() = !isLoading &&
                id.isNotBlank() &&
                password.isNotBlank() &&
                idError == null &&
                passwordError == null
}