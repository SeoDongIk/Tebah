package com.example.data.source.remote


import com.example.domain.model.Comment
import com.example.domain.model.CommentWithReplies
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteCommentDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : RemoteCommentDataSource {

//    override suspend fun addComment(postId: String, content: String): Result<Comment> = runCatching {
//        val comment = CommentDto.generate(postId, content)
//        firestore.collection("posts")
//            .document(postId)
//            .collection("comments")
//            .document(comment.id)
//            .set(comment).await()
//        comment.toDomain()
//    }
//
//    override suspend fun getComments(postId: String): Result<List<CommentWithReplies>> = runCatching {
//        val commentDocs = firestore.collection("posts")
//            .document(postId)
//            .collection("comments")
//            .get().await()
//
//        commentDocs.documents.mapNotNull { doc ->
//            val comment = doc.toObject(Comment::class.java)
//            if (comment != null) CommentWithReplies(comment, emptyList()) else null
//        }
//    }
}