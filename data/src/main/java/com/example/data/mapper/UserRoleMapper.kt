package com.example.data.mapper

import com.example.data.UserRoleProto
import com.example.domain.model.UserRole

fun UserRoleProto.toDomain(): UserRole = when(this) {
    UserRoleProto.GUEST -> UserRole.GUEST
    UserRoleProto.MEMBER -> UserRole.MEMBER
    UserRoleProto.ADMIN -> UserRole.ADMIN
    UserRoleProto.UNRECOGNIZED -> UserRole.GUEST
}

fun UserRole.toProto(): UserRoleProto = when(this) {
    UserRole.GUEST -> UserRoleProto.GUEST
    UserRole.MEMBER -> UserRoleProto.MEMBER
    UserRole.ADMIN -> UserRoleProto.ADMIN
}