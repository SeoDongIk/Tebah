package com.example.data.mapper

import com.example.data.model.dto.PostDto
import com.example.data.model.entity.PostEntity
import com.example.domain.model.Post
import com.example.domain.model.PostCreateRequest
import com.example.domain.model.PostType

fun PostDto.toDomain(): Post = Post(
    id, authorId, authorName, authorProfileUrl,
    content, imageUrls, createdAt,
    PostType.valueOf(type),
    churchId, requestedChannelIds, approvedChannelIds,
    likeCount, saveCount, checkCount, commentCount
)

fun Post.toDto(): PostDto = PostDto(
    id, authorId, authorName, authorProfileUrl,
    content, imageUrls, createdAt,
    type.name,
    churchId, requestedChannelIds, approvedChannelIds,
    likeCount, saveCount, checkCount, commentCount
)

fun PostEntity.toDomain(): Post = Post(
    id, authorId, authorName, authorProfileUrl,
    content, imageUrls, createdAt,
    PostType.valueOf(type),
    churchId, requestedChannelIds, approvedChannelIds,
    likeCount, saveCount, checkCount, commentCount
)

fun Post.toEntity(): PostEntity = PostEntity(
    id, authorId, authorName, authorProfileUrl,
    content, imageUrls, createdAt,
    type.name,
    churchId, requestedChannelIds, approvedChannelIds,
    likeCount, saveCount, checkCount, commentCount
)

fun PostDto.toEntity(): PostEntity = PostEntity(
    id, authorId, authorName, authorProfileUrl,
    content, imageUrls, createdAt,
    type,
    churchId, requestedChannelIds, approvedChannelIds,
    likeCount, saveCount, checkCount, commentCount
)

fun PostEntity.toDto(): PostDto = PostDto(
    id, authorId, authorName, authorProfileUrl,
    content, imageUrls, createdAt,
    type,
    churchId, requestedChannelIds, approvedChannelIds,
    likeCount, saveCount, checkCount, commentCount
)

fun PostCreateRequest.toDto(id: String): PostDto {
    return PostDto(
        id = id,
        authorId = this.authorId,
        authorName = this.authorName,
        authorProfileUrl = this.authorProfileUrl,
        content = this.content,
        imageUrls = this.imageUrls,
        createdAt = this.createdAt,
        type = this.type.name,
        churchId = this.churchId,
        requestedChannelIds = this.requestedChannelIds,
        approvedChannelIds = emptyList(), // 초기엔 빈 리스트
        likeCount = 0,
        saveCount = 0,
        checkCount = 0,
        commentCount = 0
    )
}