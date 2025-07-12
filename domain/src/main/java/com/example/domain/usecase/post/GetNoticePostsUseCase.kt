package com.example.domain.usecase.post

import com.example.domain.model.Post
import com.example.domain.repository.NotificationRepository
import com.example.domain.repository.PostRepository
import javax.inject.Inject

class GetNoticePostsUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
//    suspend operator fun invoke(): Result<List<Post>> {
//        return postRepository.getNoticePosts()
//    }
}