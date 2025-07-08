package com.example.data.repository

import com.example.domain.model.Channel
import com.example.domain.model.Comment
import com.example.domain.model.Post
import com.example.domain.model.UserProfile
import com.example.domain.model.UserProfileUpdateRequest
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(

) :UserRepository{
    override suspend fun getLikedPosts(): Result<List<Post>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMyPosts(): Result<List<Post>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserComments(): Result<List<Comment>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserCreatedChannels(): Result<List<Channel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserProfile(): Result<UserProfile> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserSubscribedChannels(): Result<List<Channel>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUserProfile(profile: UserProfileUpdateRequest): Result<Unit> {
        TODO("Not yet implemented")
    }
}