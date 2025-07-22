package com.example.presentation.shared.feature.channel.state

import com.example.domain.model.Channel
import com.example.domain.model.Post
import com.example.presentation.shared.component.PostData

data class ChannelDetailState(
    val channel: Channel? = null,
    val posts: List<PostData> = emptyList(),
    val subscriptionState: ChannelSubscriptionState = ChannelSubscriptionState.NOT_SUBSCRIBED,
    val isChannelOwner: Boolean = false,
    val isLoading: Boolean = false,
    val isPostButtonVisible: Boolean = false // 글쓰기 버튼 조건
)