package com.example.domain.model

data class MemberSignUpRequest(
    val email: String,
    val password: String,
    val name: String,
    val churchId: String
)