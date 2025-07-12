package com.example.data.source.remote

import com.example.data.model.dto.PostDto
import com.example.domain.model.PostCreateRequest

interface PostRemoteDataSource {

    suspend fun createPost(request: PostCreateRequest): Result<PostDto>

    suspend fun getPostById(postId: String): Result<PostDto>

    suspend fun getPostsByChurchId(churchId: String): Result<List<PostDto>>

    suspend fun getPostsByChannelId(channelId: String): Result<List<PostDto>>

    suspend fun getGlobalPosts(): Result<List<PostDto>>

    suspend fun deletePost(postId: String): Result<Unit>
}