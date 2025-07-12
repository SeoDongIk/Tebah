package com.example.data.source.local.impl

import com.example.data.model.entity.PostEntity
import com.example.data.source.local.PostLocalDataSource
import com.example.data.source.local.database.dao.PostDao
import javax.inject.Inject

class PostLocalDataSourceImpl @Inject constructor(
    private val dao: PostDao
) : PostLocalDataSource {

    override suspend fun savePosts(posts: List<PostEntity>) {
        dao.insertAll(posts)
    }

    override suspend fun getPostById(postId: String): PostEntity? = dao.getPostById(postId)

    override suspend fun getPostsByChurchId(churchId: String): List<PostEntity> = dao.getPostsByChurchId(churchId)

    override suspend fun getPostsByChannelId(channelId: String): List<PostEntity> = dao.getPostsByChannelId(channelId)

    override suspend fun getGlobalPosts(): List<PostEntity> = dao.getGlobalPosts()

    override suspend fun deletePost(postId: String) {
        dao.deletePost(postId)
    }
}