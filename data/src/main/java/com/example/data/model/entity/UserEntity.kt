package com.example.data.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.UserProfile

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val email: String,
    val name: String,
    val role: String,
    val isApproved: Boolean?,
    val churchId: String,
    @Embedded val profile: UserProfile? = null
)