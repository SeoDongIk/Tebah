package com.example.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.entity.ChurchEntity

@Dao
interface ChurchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(church: ChurchEntity)

    @Query("SELECT * FROM churches WHERE id = :churchId LIMIT 1")
    suspend fun getChurchById(churchId: String): ChurchEntity?

    @Query("DELETE FROM churches WHERE id = :churchId")
    suspend fun deleteChurchById(churchId: String)
}