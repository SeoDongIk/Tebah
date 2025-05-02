package com.example.domain.usecase.comment

import com.example.domain.model.Comment
import com.example.domain.repository.CommentRepository
import javax.inject.Inject

class AddCommentUseCase @Inject constructor(
    private val commentRepository: CommentRepository
) {
    suspend operator fun invoke(postId: String, content: String): Result<Comment> {
        return commentRepository.addComment(postId, content)
    }
}