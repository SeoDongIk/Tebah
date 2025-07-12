package com.example.data.source.remote.impl

import com.example.data.mapper.toDto
import com.example.data.model.dto.PostDto
import com.example.data.source.remote.PostRemoteDataSource
import com.example.domain.model.PostCreateRequest
import com.example.domain.model.PostType
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : PostRemoteDataSource {

    override suspend fun createPost(request: PostCreateRequest): Result<PostDto> = runCatching {
        val docRef = firestore.collection("posts").document()
        val dto = request.toDto(docRef.id)
        docRef.set(dto).await()
        dto
    }

    override suspend fun getPostById(postId: String): Result<PostDto> = runCatching {
        val snapshot = firestore.collection("posts").document(postId).get().await()
        snapshot.toObject(PostDto::class.java) ?: throw NoSuchElementException("Post not found")
    }

    override suspend fun getPostsByChurchId(churchId: String): Result<List<PostDto>> = runCatching {
        firestore.collection("posts")
            .whereEqualTo("churchId", churchId)
            .get().await().documents.mapNotNull { it.toObject(PostDto::class.java) }
    }

    override suspend fun getPostsByChannelId(channelId: String): Result<List<PostDto>> = runCatching {
        firestore.collection("posts")
            .whereArrayContains("approvedChannelIds", channelId)
            .get().await().documents.mapNotNull { it.toObject(PostDto::class.java) }
    }

    override suspend fun getGlobalPosts(): Result<List<PostDto>> = runCatching {
        firestore.collection("posts")
            .whereEqualTo("type", PostType.GLOBAL.name)
            .get().await().documents.mapNotNull { it.toObject(PostDto::class.java) }
    }

    override suspend fun deletePost(postId: String): Result<Unit> = runCatching {
        firestore.collection("posts").document(postId).delete().await()
    }
}