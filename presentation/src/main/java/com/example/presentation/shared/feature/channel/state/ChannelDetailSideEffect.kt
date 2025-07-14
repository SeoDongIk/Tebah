package com.example.presentation.shared.feature.channel.state

sealed interface ChannelDetailSideEffect {
    object NavigateToManageScreen : ChannelDetailSideEffect
    object NavigateToWriteScreen : ChannelDetailSideEffect
    data class ShowToast(val message: String) : ChannelDetailSideEffect
    object ShowSubscribeDialog : ChannelDetailSideEffect
    object ShowUnsubscribeDialog : ChannelDetailSideEffect
}