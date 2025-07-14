package com.example.presentation.shared.feature.channel.viewmodel

import androidx.lifecycle.ViewModel
import com.example.presentation.shared.feature.channel.state.ChannelDetailSideEffect
import com.example.presentation.shared.feature.channel.state.ChannelDetailState
import com.example.presentation.shared.feature.channel.state.ChannelSubscriptionState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ChannelDetailViewModel @Inject constructor(
//    private val getChannelDetailUseCase: GetChannelDetailUseCase,
//    private val getChannelPostsUseCase: GetChannelPostsUseCase,
//    private val getChannelSubscriptionStateUseCase: GetChannelSubscriptionStateUseCase,
//    private val subscribeToChannelUseCase: SubscribeToChannelUseCase,
//    private val unsubscribeFromChannelUseCase: UnsubscribeFromChannelUseCase,
//    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel(), ContainerHost<ChannelDetailState, ChannelDetailSideEffect> {

    override val container = container<ChannelDetailState, ChannelDetailSideEffect>(
        ChannelDetailState()
    )
//
//    fun loadChannel(channelId: String) = intent {
//        reduce { state.copy(isLoading = true) }
//
//        val userId = getCurrentUserIdUseCase()
//
//        val channel = getChannelDetailUseCase(channelId).getOrNull()
//        val posts = getChannelPostsUseCase(channelId).getOrElse { emptyList() }
//        val subscription = getChannelSubscriptionStateUseCase(channelId).getOrDefault(
//            ChannelSubscriptionState.NOT_SUBSCRIBED)
//
//        val isOwner = channel?.ownerId == userId
//
//        reduce {
//            state.copy(
//                channel = channel,
//                posts = posts,
//                subscriptionState = if (isOwner) ChannelSubscriptionState.SUBSCRIBED else subscription,
//                isChannelOwner = isOwner,
//                isLoading = false,
//                isPostButtonVisible = isOwner || subscription == ChannelSubscriptionState.SUBSCRIBED
//            )
//        }
//    }
//
//    fun onClickSubscribe() = intent {
//        postSideEffect(ChannelDetailSideEffect.ShowSubscribeDialog)
//    }
//
//    fun onClickUnsubscribe() = intent {
//        postSideEffect(ChannelDetailSideEffect.ShowUnsubscribeDialog)
//    }
//
//    fun confirmSubscribe() = intent {
//        val result = subscribeToChannelUseCase(state.channel?.id.orEmpty())
//        if (result.isSuccess) {
//            reduce {
//                state.copy(
//                    subscriptionState = ChannelSubscriptionState.SUBSCRIBED,
//                    isPostButtonVisible = true
//                )
//            }
//        } else {
//            postSideEffect(ChannelDetailSideEffect.ShowToast("구독에 실패했습니다."))
//        }
//    }
//
//    fun confirmUnsubscribe() = intent {
//        val result = unsubscribeFromChannelUseCase(state.channel?.id.orEmpty())
//        if (result.isSuccess) {
//            reduce {
//                state.copy(
//                    subscriptionState = ChannelSubscriptionState.NOT_SUBSCRIBED,
//                    isPostButtonVisible = false
//                )
//            }
//        } else {
//            postSideEffect(ChannelDetailSideEffect.ShowToast("구독 취소에 실패했습니다."))
//        }
//    }
//
//    fun onClickWrite() = intent {
//        postSideEffect(ChannelDetailSideEffect.NavigateToWriteScreen)
//    }
//
//    fun onClickManage() = intent {
//        postSideEffect(ChannelDetailSideEffect.NavigateToManageScreen)
//    }
}