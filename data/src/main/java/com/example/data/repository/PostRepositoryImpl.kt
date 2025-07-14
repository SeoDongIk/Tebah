package com.example.data.repository

import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntity
import com.example.data.source.local.PostLocalDataSource
import com.example.data.source.remote.PostRemoteDataSource
import com.example.domain.model.Post
import com.example.domain.model.PostCreateRequest
import com.example.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val remote: PostRemoteDataSource,
    private val local: PostLocalDataSource
) : PostRepository {

    override suspend fun createPost(request: PostCreateRequest): Result<Post> {
        return remote.createPost(request)
            .onSuccess {
                local.savePosts(listOf(it.toEntity()))
            }.map { it.toDomain() }
    }

    override suspend fun getPostById(postId: String): Result<Post> {
        return remote.getPostById(postId)
            .onSuccess {
                local.savePosts(listOf(it.toEntity()))
            }.map { it.toDomain() }
    }

    override suspend fun getPostsByChurchId(churchId: String): Result<List<Post>> {
        return remote.getPostsByChurchId(churchId)
            .onSuccess {
                local.savePosts(it.map { dto -> dto.toEntity() })
            }.map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getPostsByChannelId(channelId: String): Result<List<Post>> {
        return remote.getPostsByChannelId(channelId)
            .onSuccess {
                local.savePosts(it.map { dto -> dto.toEntity() })
            }.map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getGlobalPosts(): Result<List<Post>> {
        return remote.getGlobalPosts()
            .onSuccess {
                local.savePosts(it.map { dto -> dto.toEntity() })
            }.map { list -> list.map { it.toDomain() } }
    }

    override suspend fun deletePost(postId: String): Result<Unit> {
        return remote.deletePost(postId)
            .onSuccess {
                local.deletePost(postId)
            }
    }

}
