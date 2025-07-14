package com.example.presentation.auth.state

data class UserSignUpState(
    override val id: String = "",
    override val password: String = "",
    override val repeatPassword: String = "",
    val name: String = "",
    val selectedRegion: String = "전체",
    val churchesByRegion: Map<String, List<String>> = emptyMap(),
    val selectedChurchId: String? = null,
    val isPasswordMismatchDialogShown: Boolean = false,
    val isIdDuplicateDialogShown: Boolean = false
) : SignUpState