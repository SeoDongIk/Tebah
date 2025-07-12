package com.example.data.source.remote.impl

import com.example.data.mapper.toDto
import com.example.data.model.dto.CommentDto
import com.example.data.source.remote.CommentRemoteDataSource
import com.example.domain.model.Comment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CommentRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : CommentRemoteDataSource {

    override suspend fun createComment(postId: String, comment: Comment): Result<Unit> {
        return try {
            val docRef = firestore.collection("posts")
                .document(postId)
                .collection("comments")
                .document()

            val commentWithId = comment.copy(id = docRef.id)
            docRef.set(commentWithId.toDto()).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCommentsByPost(postId: String): Result<List<CommentDto>> {
        return try {
            val snapshot = firestore.collection("posts")
                .document(postId)
                .collection("comments")
                .orderBy("createdAt")
                .get()
                .await()

            val comments = snapshot.toObjects(CommentDto::class.java)
            Result.success(comments)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteComment(commentId: String): Result<Unit> {
        // 이건 postId도 필요할 수 있음. 구조에 따라 달라져
        return Result.failure(NotImplementedError("삭제 로직은 postId 필요"))
    }
}