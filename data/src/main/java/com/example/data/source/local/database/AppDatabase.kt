package com.example.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.model.entity.ChannelEntity
import com.example.data.model.entity.ChurchEntity
import com.example.data.model.entity.CommentEntity
import com.example.data.model.entity.NotificationEntity
import com.example.data.model.entity.PostEntity
import com.example.data.model.entity.UserEntity
import com.example.data.source.local.database.dao.ChannelDao
import com.example.data.source.local.database.dao.ChurchDao
import com.example.data.source.local.database.dao.CommentDao
import com.example.data.source.local.database.dao.NotificationDao
import com.example.data.source.local.database.dao.PostDao
import com.example.data.source.local.database.dao.UserDao

@Database(
    entities = [
        PostEntity::class,
        ChannelEntity::class,
        ChurchEntity::class,
        CommentEntity::class,
        NotificationEntity::class,
        UserEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
    abstract fun channelDao(): ChannelDao
    abstract fun churchDao(): ChurchDao
    abstract fun commentDao(): CommentDao
    abstract fun notificationDao(): NotificationDao
    abstract fun userDao(): UserDao
}