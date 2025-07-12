package com.example.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.entity.CommentEntity

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(comments: List<CommentEntity>)

    @Query("SELECT * FROM comments WHERE postId = :postId ORDER BY createdAt ASC")
    suspend fun getCommentsByPostId(postId: String): List<CommentEntity>

    @Query("DELETE FROM comments WHERE id = :commentId")
    suspend fun deleteComment(commentId: String)
}