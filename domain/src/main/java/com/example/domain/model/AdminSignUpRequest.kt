package com.example.domain.model

data class AdminSignUpRequest(
    val church: ChurchInfo,
    val adminName: String,
    val adminEmail: String,
    val adminPassword: String,
    val adminPosition: AdminPosition,
    val customPosition: String? = null
)