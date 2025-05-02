package com.example.domain.usecase.channel

import com.example.domain.model.Banner
import com.example.domain.repository.ChannelRepository
import javax.inject.Inject

class DeleteChannelUseCase @Inject constructor(
    private val channelRepository: ChannelRepository
) {
    suspend operator fun invoke(channelId: String): Result<Unit> {
        return channelRepository.deleteChannel(channelId)
    }
}