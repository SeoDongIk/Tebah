package com.example.domain.usecase.post

import com.example.domain.model.Post
import com.example.domain.repository.PostRepository
import javax.inject.Inject

class GetRecommendedPostsUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(): Result<List<Post>> {
        return postRepository.getRecommendedPosts()
    }
}