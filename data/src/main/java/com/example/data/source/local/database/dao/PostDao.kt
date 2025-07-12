package com.example.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.entity.PostEntity

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostEntity>)

    @Query("SELECT * FROM posts WHERE id = :postId")
    suspend fun getPostById(postId: String): PostEntity?

    @Query("SELECT * FROM posts WHERE churchId = :churchId")
    suspend fun getPostsByChurchId(churchId: String): List<PostEntity>

    @Query("SELECT * FROM posts WHERE :channelId IN (approvedChannelIds)")
    suspend fun getPostsByChannelId(channelId: String): List<PostEntity>

    @Query("SELECT * FROM posts WHERE type = 'GLOBAL'")
    suspend fun getGlobalPosts(): List<PostEntity>

    @Query("DELETE FROM posts WHERE id = :postId")
    suspend fun deletePost(postId: String)
}

