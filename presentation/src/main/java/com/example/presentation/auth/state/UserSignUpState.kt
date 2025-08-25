package com.example.presentation.auth.state

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import com.example.presentation.common.state.MediumDialogState

@Stable
data class UserSignUpState(
    override val id: String = "",
    override val password: String = "",
    override val repeatPassword: String = "",
    override val name: String = "",
    val selectedRegion: String = "전체",
    val churchesByRegion: Map<String, List<String>> = emptyMap(),
    val selectedChurchId: String? = null,
    @StringRes val nameError: Int? = null,
    @StringRes val idError: Int? = null,
    @StringRes val passwordError: Int? = null,
    @StringRes val repeatPasswordError: Int? = null,
    val dialog: MediumDialogState? = null,
    override val isSigningUp: Boolean = false,
    override val isSigningIn: Boolean = false
) : SignUpState {

    /** 멤버정보 입력 화면의 다음 버튼 활성 조건 */
    val isMemberInfoValid: Boolean
        get() = name.isNotBlank() &&
                id.isNotBlank() &&
                password.isNotBlank() &&
                repeatPassword.isNotBlank() &&
                nameError == null &&
                idError == null &&
                passwordError == null &&
                repeatPasswordError == null

    /** 교회 선택 화면 완료 활성 조건 */
    val isChurchSelectValid: Boolean
        get() = !selectedChurchId.isNullOrBlank()
}