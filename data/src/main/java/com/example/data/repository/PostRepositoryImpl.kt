package com.example.data.repository

import com.example.domain.model.CreatePostRequest
import com.example.domain.model.Post
import com.example.domain.model.PostDetail
import com.example.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(

) :PostRepository {
    override suspend fun createPost(request: CreatePostRequest): Result<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun getNoticePosts(): Result<List<Post>> {
        TODO("Not yet implemented")
    }

    override suspend fun getNoticesByChannel(channelId: String): Result<List<Post>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPersonalizedRecommendedPosts(userId: String): Result<List<Post>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostDetail(postId: String): Result<PostDetail> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecentPosts(): Result<List<Post>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecommendedPosts(): Result<List<Post>> {
        TODO("Not yet implemented")
    }

    override suspend fun checkPost(postId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun likePost(postId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun savePost(postId: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun searchPost(keyword: String): Result<List<Post>> {
        TODO("Not yet implemented")
    }
}