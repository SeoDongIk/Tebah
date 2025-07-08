package com.example.domain.usecase.channel

import com.example.domain.model.Banner
import com.example.domain.model.RootChannel
import com.example.domain.repository.ChannelRepository
import javax.inject.Inject

class GetAllRootChannelsUseCase @Inject constructor(
    private val channelRepository: ChannelRepository
) {
    suspend operator fun invoke(): Result<List<RootChannel>> {
        return channelRepository.getAllRootChannels()
    }
}