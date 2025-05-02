package com.example.domain.repository

import com.example.domain.model.Banner

interface BannerRepository {
    suspend fun getImageBanners(): Result<List<Banner>>
    suspend fun getMainBanners(): Result<List<Banner>>
}
