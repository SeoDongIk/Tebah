package com.example.domain.usecase.channel

import com.example.domain.model.Banner
import com.example.domain.model.Channel
import com.example.domain.repository.ChannelRepository
import javax.inject.Inject

class GetChannelsBySearchTermUseCase @Inject constructor(
    private val channelRepository: ChannelRepository
) {
    suspend operator fun invoke(query: String): Result<List<Channel>> {
        return channelRepository.getChannelsBySearchTerm(query)
    }
}