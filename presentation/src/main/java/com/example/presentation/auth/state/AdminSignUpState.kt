package com.example.presentation.auth.state

import androidx.compose.runtime.Immutable
import com.example.domain.model.AdminPosition
import com.example.domain.model.Region

@Immutable
data class AdminSignUpState(
    override val id: String = "",
    override val password: String = "",
    override val repeatPassword: String = "",
    val adminName: String = "",
    val adminRole: AdminPosition = AdminPosition.PASTOR,
    val customRole: String = "",
    val churchName: String = "",
    val region: Region? = null,
    val phoneNumber: String = "",
    val churchIntro: String = ""
) : SignUpState {

    val isCustomInput: Boolean
        get() = adminRole == AdminPosition.CUSTOM

    // 회원 정보 관련 유효성 체크 (ID, PW, 이름, 커스텀 역할)
    val isUserInfoValid: Boolean
        get() = adminName.isNotBlank()
                && id.isNotBlank()
                && password.isNotBlank()
                && repeatPassword.isNotBlank()
                && password == repeatPassword
                && (if (isCustomInput) customRole.isNotBlank() else true)

    // 교회 정보 관련 유효성 체크 (이 화면에서만 사용)
    val isChurchInfoValid: Boolean
        get() = churchName.isNotBlank()
                && phoneNumber.isNotBlank()
                && churchIntro.isNotBlank()
                && region != null
}