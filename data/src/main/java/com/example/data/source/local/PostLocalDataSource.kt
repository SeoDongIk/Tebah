package com.example.data.source.local

import com.example.data.model.entity.PostEntity

interface PostLocalDataSource {

    suspend fun savePosts(posts: List<PostEntity>)

    suspend fun getPostById(postId: String): PostEntity?

    suspend fun getPostsByChurchId(churchId: String): List<PostEntity>

    suspend fun getPostsByChannelId(channelId: String): List<PostEntity>

    suspend fun getGlobalPosts(): List<PostEntity>

    suspend fun deletePost(postId: String)
}