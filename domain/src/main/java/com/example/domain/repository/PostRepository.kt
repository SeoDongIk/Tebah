package com.example.domain.repository

import com.example.domain.model.CreatePostRequest
import com.example.domain.model.Post
import com.example.domain.model.PostDetail

interface PostRepository {
    suspend fun createPost(request: CreatePostRequest): Result<Post>
    suspend fun getNoticePosts(): Result<List<Post>>
    suspend fun getNoticesByChannel(channelId: String): Result<List<Post>>
    suspend fun getPersonalizedRecommendedPosts(userId: String): Result<List<Post>>
    suspend fun getPostDetail(postId: String): Result<PostDetail>
    suspend fun getRecentPosts(): Result<List<Post>>
    suspend fun getRecommendedPosts(): Result<List<Post>>
    suspend fun checkPost(postId: String): Result<Unit>
    suspend fun likePost(postId: String): Result<Unit>
    suspend fun savePost(postId: String): Result<Unit>
    suspend fun searchPost(keyword: String): Result<List<Post>>
}