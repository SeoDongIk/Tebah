package com.example.domain.repository

import com.example.domain.model.Post
import com.example.domain.model.PostCreateRequest
import com.example.domain.model.PostPreviewItem

interface PostRepository {

    suspend fun createPost(request: PostCreateRequest): Result<Post>

    suspend fun getPostById(postId: String): Result<Post>

    suspend fun getPostsByChurchId(churchId: String): Result<List<Post>>

    suspend fun getPostsByChannelId(channelId: String): Result<List<Post>>

    suspend fun getGlobalPosts(): Result<List<Post>>

    suspend fun deletePost(postId: String): Result<Unit>
}