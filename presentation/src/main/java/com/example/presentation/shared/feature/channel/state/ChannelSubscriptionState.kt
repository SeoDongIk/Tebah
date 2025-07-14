package com.example.presentation.shared.feature.channel.state

enum class ChannelSubscriptionState {
    NOT_SUBSCRIBED, // 구독 X
    SUBSCRIBED,     // 구독 중
    REQUESTED,      // 구독 요청 중 (공식 채널)
    REJECTED        // 요청 거절됨 (필요하면)
}
