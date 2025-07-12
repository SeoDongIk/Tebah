package com.example.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "churches")
data class ChurchEntity(
    @PrimaryKey val id: String,
    val name: String,
    val profileImageUrl: String?,
    val region: String,
    val phone: String,
    val description: String,
    val createdAt: Long, // Timestamp를 millis 로 변환해 저장
    val adminId: String,
    val adminName: String,
    val adminPosition: String
)