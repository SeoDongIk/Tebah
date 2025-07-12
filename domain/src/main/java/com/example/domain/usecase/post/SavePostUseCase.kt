package com.example.domain.usecase.post

import com.example.domain.repository.PostRepository
import javax.inject.Inject

class SavePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
//    suspend operator fun invoke(postId: String): Result<Unit> {
//        return postRepository.savePost(postId)
//    }
}