package com.example.domain.usecase.user

import com.example.domain.model.Comment
import com.example.domain.model.Post
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class GetUserCommentUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<Comment>> {
        return userRepository.getUserComments()
    }
}