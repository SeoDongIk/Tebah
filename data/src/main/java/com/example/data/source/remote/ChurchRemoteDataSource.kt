package com.example.data.source.remote

import com.example.data.model.dto.ChurchDto

interface ChurchRemoteDataSource {
    suspend fun createChurch(church: ChurchDto): Result<Unit>
    suspend fun getChurchById(churchId: String): Result<ChurchDto>
    suspend fun getApprovedChurchList(): Result<List<ChurchDto>>
}