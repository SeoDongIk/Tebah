package com.example.presentation.auth.state

import androidx.compose.ui.graphics.Color
import com.example.domain.model.UserRole
import com.example.presentation.common.theme.primary

data class SignInState(
    val id: String = "",
    val password: String = "",
    val role: UserRole? = null,
    val autoLogin: Boolean = false,
) {
    val isLoginEnabled: Boolean
        get() = id.isNotBlank()
                && password.length >= 6
                && android.util.Patterns.EMAIL_ADDRESS.matcher(id).matches()

    val loginButtonColor: Color
        get() = if (isLoginEnabled) primary else Color.LightGray
}
