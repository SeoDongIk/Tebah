package com.example.domain.usecase.network

import com.example.domain.repository.NetworkRepository
import javax.inject.Inject

class CheckNetworkUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    suspend operator fun invoke(): Boolean = networkRepository.isNetworkAvailable()
}