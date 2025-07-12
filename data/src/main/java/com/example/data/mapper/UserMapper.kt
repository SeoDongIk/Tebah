package com.example.data.mapper

import com.example.data.model.dto.UserDto
import com.example.data.model.dto.UserProfileDto
import com.example.data.model.entity.UserEntity
import com.example.domain.model.User
import com.example.domain.model.UserProfile
import com.example.domain.model.UserRole

fun UserDto.toEntity(): UserEntity = UserEntity(
    id, email, name, role, isApproved, churchId,
    profile = profile?.let { UserProfile(it.imageUrl, it.introduction) }
)

fun UserEntity.toDomain(): User = User(
    id, email, name, UserRole.fromString(role), isApproved, churchId, profile
)

fun User.toDto(): UserDto = UserDto(
    id, email, name, role.name, isApproved, churchId,
    profile = profile?.let { UserProfileDto(it.imageUrl, it.introduction) }
)

fun UserDto.toDomain(): User = User(
    id, email, name, UserRole.fromString(role), isApproved, churchId,
    profile = profile?.let { UserProfile(it.imageUrl, it.introduction) }
)