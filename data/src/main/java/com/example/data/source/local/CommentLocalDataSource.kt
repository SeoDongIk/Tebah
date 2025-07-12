package com.example.data.source.local

import com.example.data.model.entity.CommentEntity

interface CommentLocalDataSource {
    suspend fun saveComments(comments: List<CommentEntity>)
    suspend fun getCommentsByPostId(postId: String): List<CommentEntity>
    suspend fun deleteComment(commentId: String)
}