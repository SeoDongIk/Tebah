package com.example.domain.usecase.post

import com.example.domain.model.Post
import com.example.domain.model.PostDetail
import com.example.domain.repository.PostRepository
import javax.inject.Inject

class GetPostDetailUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: String): Result<PostDetail> {
        return postRepository.getPostDetail(postId)
    }
}