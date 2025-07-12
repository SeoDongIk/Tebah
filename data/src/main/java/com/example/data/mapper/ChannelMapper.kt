package com.example.data.mapper

import com.example.data.model.dto.ChannelDto
import com.example.data.model.entity.ChannelEntity
import com.example.domain.model.Channel

fun ChannelDto.toEntity(): ChannelEntity = ChannelEntity(
    id, name, description, isOfficial, createdAt, churchId, ownerId
)

fun ChannelEntity.toDomain(): Channel = Channel(
    id, name, description, isOfficial, createdAt, churchId, ownerId
)

fun ChannelDto.toDomain(): Channel = Channel(
    id, name, description, isOfficial, createdAt, churchId, ownerId
)

fun Channel.toDto(): ChannelDto = ChannelDto(
    id, name, description, isOfficial, createdAt, churchId, ownerId
)

fun Channel.toEntity(): ChannelEntity = ChannelEntity(
    id, name, description, isOfficial, createdAt, churchId, ownerId
)