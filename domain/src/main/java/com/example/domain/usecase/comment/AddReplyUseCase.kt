package com.example.domain.usecase.comment

import com.example.domain.model.Comment
import com.example.domain.repository.CommentRepository
import javax.inject.Inject

class AddReplyUseCase @Inject constructor(
    private val commentRepository: CommentRepository
) {
//    suspend operator fun invoke(commentId: String, content: String): Result<Reply> {
//        return commentRepository.addReply(commentId, content)
//    }
}