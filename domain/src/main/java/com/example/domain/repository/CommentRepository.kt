package com.example.domain.repository

import com.example.domain.model.Comment
import com.example.domain.model.CommentWithReplies
import com.example.domain.model.Reply

interface CommentRepository {
    suspend fun addComment(postId: String, content: String): Result<Comment>
    suspend fun addReply(commentId: String, content: String): Result<Reply>
    suspend fun getCommentForPost(postId: String): Result<List<CommentWithReplies>>
}