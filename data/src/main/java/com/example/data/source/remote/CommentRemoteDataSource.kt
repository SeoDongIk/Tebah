package com.example.data.source.remote

import com.example.data.model.dto.CommentDto
import com.example.domain.model.Comment

interface CommentRemoteDataSource {
    suspend fun createComment(postId: String, comment: Comment): Result<Unit>
    suspend fun getCommentsByPost(postId: String): Result<List<CommentDto>>
    suspend fun deleteComment(commentId: String): Result<Unit>
}