package com.example.domain.repository

import com.example.domain.model.Channel
import com.example.domain.model.Comment
import com.example.domain.model.Post
import com.example.domain.model.UserProfile
import com.example.domain.model.UserProfileUpdateRequest

interface UserRepository {
    suspend fun getLikedPosts(): Result<List<Post>>
    suspend fun getMyPosts(): Result<List<Post>>
    suspend fun getUserComments(): Result<List<Comment>>
    suspend fun getUserCreatedChannels(): Result<List<Channel>>
    suspend fun getUserProfile(): Result<UserProfile>
    suspend fun getUserSubscribedChannels(): Result<List<Channel>>
    suspend fun updateUserProfile(profile: UserProfileUpdateRequest): Result<Unit>
}