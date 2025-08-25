package com.example.presentation.auth.state

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import com.example.domain.model.AdminPosition
import com.example.domain.model.Region
import com.example.presentation.common.state.MediumDialogState

@Stable
data class AdminSignUpState(
    val churchName: String = "",
    val region: Region? = null,
    val phoneNumber: String = "",
    val churchIntro: String = "",
    @StringRes val churchNameError: Int? = null, // null 여부로 isError 여부 판단
    @StringRes val regionError: Int? = null, // null 여부로 isError 여부 판단
    @StringRes val phoneNumberError: Int? = null, // null 여부로 isError 여부 판단
    @StringRes val churchIntroError: Int? = null, // null 여부로 isError 여부 판단
    override val name: String = "",
    override val id: String = "",
    override val password: String = "",
    override val repeatPassword: String = "",
    val adminRole: AdminPosition = AdminPosition.PASTOR,
    val customRole: String = "",
    @StringRes val nameError: Int? = null, // null 여부로 isError 여부 판단
    @StringRes val idError: Int? = null, // null 여부로 isError 여부 판단
    @StringRes val passwordError: Int? = null, // null 여부로 isError 여부 판단
    @StringRes val repeatPasswordError: Int? = null, // null 여부로 isError 여부 판단
    @StringRes val customRoleError: Int? = null, // null 여부로 isError 여부 판단
    val regionSelectorVisible: Boolean = false,
    val dialog: MediumDialogState? = null,
    override val isSigningUp: Boolean = false,
    override val isSigningIn: Boolean = false
) : SignUpState {

    val isCustomInput: Boolean
        get() = adminRole == AdminPosition.CUSTOM

    val isChurchInfoValid: Boolean
        get() =
            churchName.isNotBlank() &&
                    phoneNumber.isNotBlank() &&
                    churchIntro.isNotBlank() &&
                    region != null &&
                    churchNameError == null &&
                    phoneNumberError == null &&
                    churchIntroError == null &&
                    regionError == null

    val isUserInfoValid: Boolean
        get() =
            name.isNotBlank() &&
                    id.isNotBlank() &&
                    password.isNotBlank() &&
                    repeatPassword.isNotBlank() &&
                    ( !isCustomInput || customRole.isNotBlank() ) &&
                    nameError == null &&
                    idError == null &&
                    passwordError == null &&
                    repeatPasswordError == null &&
                    ( !isCustomInput || customRoleError == null )
}