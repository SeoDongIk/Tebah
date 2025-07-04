package com.example.data.source.local

import com.example.data.dao.PostDao
import com.example.domain.model.Post
import javax.inject.Inject

class LocalPostDataSourceImpl @Inject constructor(
    private val postDao: PostDao
) : LocalPostDataSource {
//    override suspend fun savePost(post: Post) {
//        postDao.insertPosts(post.toEntity())
//    }
//
//    override suspend fun getSavedPosts(): List<Post> {
//        return postDao.getAllPosts().map { it.toDomain() }
//    }
}