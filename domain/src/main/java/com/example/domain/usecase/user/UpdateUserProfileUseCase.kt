package com.example.domain.usecase.user

import com.example.domain.model.Post
import com.example.domain.model.UserProfileUpdateRequest
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(profile: UserProfileUpdateRequest): Result<Unit> {
        return userRepository.updateUserProfile(profile)
    }
}