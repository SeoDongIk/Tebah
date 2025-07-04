package com.example.data.repository

import com.example.domain.model.Comment
import com.example.domain.model.CommentWithReplies
import com.example.domain.model.Reply
import com.example.domain.repository.CommentRepository
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(

) :CommentRepository{
    override suspend fun addComment(postId: String, content: String): Result<Comment> {
        TODO("Not yet implemented")
    }

    override suspend fun addReply(commentId: String, content: String): Result<Reply> {
        TODO("Not yet implemented")
    }

    override suspend fun getCommentForPost(postId: String): Result<List<CommentWithReplies>> {
        TODO("Not yet implemented")
    }
}