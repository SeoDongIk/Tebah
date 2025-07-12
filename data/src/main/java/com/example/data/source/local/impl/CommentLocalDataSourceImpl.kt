package com.example.data.source.local.impl

import com.example.data.model.entity.CommentEntity
import com.example.data.source.local.CommentLocalDataSource
import com.example.data.source.local.database.dao.CommentDao
import javax.inject.Inject

// ✅ Local DataSource 구현체
class CommentLocalDataSourceImpl @Inject constructor(
    private val dao: CommentDao
) : CommentLocalDataSource {

    override suspend fun saveComments(comments: List<CommentEntity>) {
        dao.insertAll(comments)
    }

    override suspend fun getCommentsByPostId(postId: String): List<CommentEntity> =
        dao.getCommentsByPostId(postId)

    override suspend fun deleteComment(commentId: String) {
        dao.deleteComment(commentId)
    }
}