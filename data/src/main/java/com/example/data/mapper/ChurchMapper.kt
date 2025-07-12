package com.example.data.mapper

import com.example.data.model.dto.ChurchDto
import com.example.data.model.entity.ChurchEntity
import com.google.firebase.Timestamp

fun ChurchDto.toEntity(): ChurchEntity = ChurchEntity(
    id = id,
    name = name,
    profileImageUrl = profileImageUrl,
    region = region,
    phone = phone,
    description = description,
    createdAt = createdAt.toDate().time,
    adminId = adminId,
    adminName = adminName,
    adminPosition = adminPosition
)

fun ChurchEntity.toDto(): ChurchDto = ChurchDto(
    id = id,
    name = name,
    profileImageUrl = profileImageUrl,
    region = region,
    phone = phone,
    description = description,
    createdAt = Timestamp(Timestamp.now().seconds, 0), // 또는 Timestamp(Date(createdAt))
    adminId = adminId,
    adminName = adminName,
    adminPosition = adminPosition
)