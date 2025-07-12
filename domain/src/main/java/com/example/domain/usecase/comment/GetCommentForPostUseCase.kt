package com.example.domain.usecase.comment

import com.example.domain.repository.CommentRepository
import javax.inject.Inject

class GetCommentForPostUseCase @Inject constructor(
    private val commentRepository: CommentRepository
) {
//    suspend operator fun invoke(postId: String): Result<List<CommentWithReplies>> {
//        return commentRepository.getCommentForPost(postId)
//    }
}