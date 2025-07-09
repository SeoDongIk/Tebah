package com.example.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.entity.PostEntity
import com.example.data.dao.PostDao

@Database(
    entities = [PostEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}