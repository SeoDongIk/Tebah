package com.example.domain.usecase.post

import com.example.domain.model.Notification
import com.example.domain.repository.NotificationRepository
import com.example.domain.repository.PostRepository
import javax.inject.Inject

class CheckPostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: String): Result<Unit> {
        return postRepository.checkPost(postId)
    }
}