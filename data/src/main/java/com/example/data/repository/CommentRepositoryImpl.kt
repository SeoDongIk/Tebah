package com.example.data.repository

import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntity
import com.example.data.source.local.CommentLocalDataSource
import com.example.data.source.local.UserLocalDataSource
import com.example.data.source.remote.CommentRemoteDataSource
import com.example.domain.model.Comment
import com.example.domain.repository.CommentRepository
import java.util.UUID
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val commentRemoteDataSource: CommentRemoteDataSource,
    private val commentLocalDataSource: CommentLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : CommentRepository {

    override suspend fun addComment(postId: String, content: String): Result<Comment> {
        val user = userLocalDataSource.getAuthUser()
            ?: return Result.failure(IllegalStateException("User not logged in"))

        val comment = Comment(
            id = UUID.randomUUID().toString(), // 임시 ID 생성
            postId = postId,
            userId = user.id,
            userName = user.name,
            userProfileUrl = user.profile?.imageUrl,
            content = content,
            createdAt = System.currentTimeMillis()
        )

        return commentRemoteDataSource.createComment(postId, comment)
            .onSuccess {
                commentLocalDataSource.saveComments(listOf(comment.toEntity()))
            }.map { comment }
    }

    override suspend fun getCommentsForPost(postId: String): Result<List<Comment>> {
        val result = commentRemoteDataSource.getCommentsByPost(postId)
        result.getOrNull()?.let {
            commentLocalDataSource.saveComments(it.map { dto -> dto.toEntity() })
        }
        return result.map { it.map { dto -> dto.toDomain() } }
    }
}