package com.example.data.source.local

import com.example.data.model.entity.ChurchEntity

interface ChurchLocalDataSource {
    suspend fun saveChurch(church: ChurchEntity)
    suspend fun getChurchById(churchId: String): ChurchEntity?
    suspend fun deleteChurch(churchId: String)
}