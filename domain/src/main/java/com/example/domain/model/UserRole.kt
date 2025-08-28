package com.example.domain.model

enum class UserRole {
    ADMIN,
    MEMBER,
    GUEST,
    UNKNOWN;

    companion object {
        fun fromString(value: String?): UserRole = when(value?.uppercase()) {
            "ADMIN" -> ADMIN
            "MEMBER" -> MEMBER
            "GUEST" -> GUEST
            else -> UNKNOWN
        }
    }
}