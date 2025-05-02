package com.example.domain.usecase.post

import com.example.domain.model.CreatePostRequest
import com.example.domain.model.Post
import com.example.domain.repository.NotificationRepository
import com.example.domain.repository.PostRepository
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(request: CreatePostRequest): Result<Post> {
        return postRepository.createPost(request)
    }
}