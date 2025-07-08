package com.example.domain.usecase.user

import com.example.domain.model.Channel
import com.example.domain.model.Post
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class GetUserCreatedChannelsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<Channel>> {
        return userRepository.getUserCreatedChannels()
    }
}