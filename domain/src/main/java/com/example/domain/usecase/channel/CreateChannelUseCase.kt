package com.example.domain.usecase.channel

import com.example.domain.repository.ChannelRepository
import javax.inject.Inject

/**
 * 루트 채널 등록이 완료된 것을 UI에 반영하는 기능.
 * 교회 이름, 지역, 설명 등과 함께 생성 대기 상태로 등록됩니다.
 */
class CreateChannelUseCase @Inject constructor(
    private val channelRepository: ChannelRepository
) {
//    suspend operator fun invoke(request: CreateChannelRequest): Result<Channel> {
//        return channelRepository.createChannel(request)
//    }
}