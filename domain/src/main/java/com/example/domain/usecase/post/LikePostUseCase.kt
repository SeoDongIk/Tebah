package com.example.domain.usecase.post

import com.example.domain.model.Post
import com.example.domain.repository.PostRepository
import javax.inject.Inject

class LikePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
//    suspend operator fun invoke(postId: String): Result<Unit> {
//        return postRepository.likePost(postId)
//    }
}