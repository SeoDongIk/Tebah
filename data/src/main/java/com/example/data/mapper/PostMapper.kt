package com.example.data.mapper

import com.example.data.model.entity.PostEntity
import com.example.domain.model.Post
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

//private val json = Json { ignoreUnknownKeys = true }
//
////
//// Dto ↔ Domain
////
//
//fun PostDto.toDomain(
//    title: String,
//    imageUrls: List<String>,
//    noticeRequestId: String?,
//    isSavedLocally: Boolean
//): Post {
//    return Post(
//        id = id,
//        authorId = authorId,
//        title = title,
//        content = content,
//        imageUrls = imageUrls,
//        isNotice = isNotice,
//        noticeRequestId = noticeRequestId,
//        createdAt = createdAt,
//        likedUserIds = likedUserIds,
//        checkedUserIds = checkedUserIds,
//        isSavedLocally = isSavedLocally
//    )
//}
//
//fun Post.toDto(): PostDto {
//    return PostDto(
//        id = id,
//        authorId = authorId,
//        content = content,
//        createdAt = createdAt,
//        checkedUserIds = checkedUserIds,
//        likedUserIds = likedUserIds,
//        isNotice = isNotice
//        // savedUserIds, commentIds, channelIds는 필요에 따라 채워야 함
//    )
//}
//
////
//// Dto ↔ Entity
////
//
//fun PostDto.toEntity(): PostEntity {
//    return PostEntity(
//        id = id,
//        authorId = authorId,
//        content = content,
//        createdAt = createdAt,
//        checkedUserIds = json.encodeToString(checkedUserIds),
//        likedUserIds = json.encodeToString(likedUserIds),
//        savedUserIds = json.encodeToString(savedUserIds),
//        commentIds = json.encodeToString(commentIds),
//        channelIds = json.encodeToString(channelIds),
//        isNotice = isNotice
//    )
//}
//
//fun PostEntity.toDto(): PostDto {
//    return PostDto(
//        id = id,
//        authorId = authorId,
//        content = content,
//        createdAt = createdAt,
//        checkedUserIds = json.decodeFromString(checkedUserIds),
//        likedUserIds = json.decodeFromString(likedUserIds),
//        savedUserIds = json.decodeFromString(savedUserIds),
//        commentIds = json.decodeFromString(commentIds),
//        channelIds = json.decodeFromString(channelIds),
//        isNotice = isNotice
//    )
//}
//
////
//// Entity ↔ Domain
////
//
//fun PostEntity.toDomain(
//    title: String,
//    imageUrls: List<String>,
//    noticeRequestId: String?,
//    isSavedLocally: Boolean
//): Post {
//    return Post(
//        id = id,
//        authorId = authorId,
//        title = title,
//        content = content,
//        imageUrls = imageUrls,
//        isNotice = isNotice,
//        noticeRequestId = noticeRequestId,
//        createdAt = createdAt,
//        likedUserIds = json.decodeFromString(likedUserIds),
//        checkedUserIds = json.decodeFromString(checkedUserIds),
//        isSavedLocally = isSavedLocally
//    )
//}
//
//fun Post.toEntity(
//    savedUserIds: List<String>,
//    commentIds: List<String>,
//    channelIds: List<String>
//): PostEntity {
//    return PostEntity(
//        id = id,
//        authorId = authorId,
//        content = content,
//        createdAt = createdAt,
//        checkedUserIds = json.encodeToString(checkedUserIds),
//        likedUserIds = json.encodeToString(likedUserIds),
//        savedUserIds = json.encodeToString(savedUserIds),
//        commentIds = json.encodeToString(commentIds),
//        channelIds = json.encodeToString(channelIds),
//        isNotice = isNotice
//    )
//}