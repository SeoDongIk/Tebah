package com.example.domain.usecase.channel

import com.example.domain.model.Channel
import com.example.domain.repository.ChannelRepository
import javax.inject.Inject

class GetChannelsByRootChannelUseCase @Inject constructor(
    private val channelRepository: ChannelRepository
) {
//    suspend operator fun invoke(rootChannelId: String): Result<List<Channel>> {
//        return channelRepository.getChannelsByRootChannel(rootChannelId)
//    }
}