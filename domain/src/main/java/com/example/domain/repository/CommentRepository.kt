package com.example.domain.repository

import com.example.domain.model.Comment

interface CommentRepository {
    suspend fun addComment(postId: String, content: String): Result<Comment>
    suspend fun getCommentsForPost(postId: String): Result<List<Comment>>
}