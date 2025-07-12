package com.example.domain.usecase.user

import com.example.domain.model.Post
import com.example.domain.model.UserProfile
import com.example.domain.repository.UserRepository
import javax.inject.Inject


class GetUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
//    suspend operator fun invoke(): Result<UserProfile> {
//        return userRepository.getUserProfile()
//    }
}