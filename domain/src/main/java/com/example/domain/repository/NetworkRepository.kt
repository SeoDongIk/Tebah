package com.example.domain.repository

interface NetworkRepository {
    suspend fun isNetworkAvailable(): Boolean
}