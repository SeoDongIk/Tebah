package com.example.domain.usecase.banner

import com.example.domain.model.Banner
import com.example.domain.repository.BannerRepository
import javax.inject.Inject

class GetMainBannersUseCase @Inject constructor(
    private val bannerRepository : BannerRepository
) {
    suspend operator fun invoke(): Result<List<Banner>> {
        return bannerRepository.getMainBanners()
    }
}