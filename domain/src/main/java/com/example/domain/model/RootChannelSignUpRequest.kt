package com.example.domain.model

data class RootChannelSignUpRequest(
    val email: String,
    val name: String,
    val churchName: String
)