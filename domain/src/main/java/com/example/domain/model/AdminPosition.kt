package com.example.domain.model

enum class AdminPosition(val displayName: String) {
    PASTOR("목사"),
    ADMIN("관리자"),
    CUSTOM("직접입력");

    companion object {
        fun fromDisplayName(name: String) = values().find { it.displayName == name } ?: CUSTOM
    }
}