package com.example.data.mapper

import com.example.data.model.dto.CommentDto
import com.example.data.model.entity.CommentEntity
import com.example.domain.model.Comment

// 🔹 Entity → Domain
fun CommentEntity.toDomain(): Comment = Comment(
    id = id,
    postId = postId,
    userId = userId,
    userName = userName,
    userProfileUrl = userProfileImageUrl,
    content = content,
    createdAt = createdAt
)

// 🔹 Domain → Entity
fun Comment.toEntity(): CommentEntity = CommentEntity(
    id = id,
    postId = postId,
    userId = userId,
    userName = userName,
    userProfileImageUrl = userProfileUrl,
    content = content,
    createdAt = createdAt
)

// 🔹 Dto → Entity
fun CommentDto.toEntity(): CommentEntity = CommentEntity(
    id = id,
    postId = postId,
    userId = userId,
    userName = userName,
    userProfileImageUrl = userProfileImageUrl,
    content = content,
    createdAt = createdAt
)

// 🔹 Entity → Dto
fun CommentEntity.toDto(): CommentDto = CommentDto(
    id = id,
    postId = postId,
    userId = userId,
    userName = userName,
    userProfileImageUrl = userProfileImageUrl,
    content = content,
    createdAt = createdAt
)

// 🔹 Dto → Domain
fun CommentDto.toDomain(): Comment = Comment(
    id = id,
    postId = postId,
    userId = userId,
    userName = userName,
    userProfileUrl = userProfileImageUrl,
    content = content,
    createdAt = createdAt
)

// 🔹 Domain → Dto
fun Comment.toDto(): CommentDto = CommentDto(
    id = id,
    postId = postId,
    userId = userId,
    userName = userName,
    userProfileImageUrl = userProfileUrl,
    content = content,
    createdAt = createdAt
)