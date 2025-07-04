package com.example.data.repository

import com.example.domain.model.Banner
import com.example.domain.repository.BannerRepository
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor(

): BannerRepository {
    override suspend fun getImageBanners(): Result<List<Banner>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMainBanners(): Result<List<Banner>> {
        TODO("Not yet implemented")
    }
}