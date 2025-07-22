package com.example.presentation.shared.feature.channel.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.Channel
import com.example.presentation.common.component.MediumButton
import com.example.presentation.common.theme.Paddings
import com.example.presentation.common.theme.TebahTypography
import com.example.presentation.shared.feature.channel.state.ChannelDetailSideEffect
import com.example.presentation.shared.feature.channel.state.ChannelDetailState
import com.example.presentation.shared.feature.channel.viewmodel.ChannelDetailViewModel
import com.example.presentation.shared.feature.post.screen.PostPreviewCard
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ChannelDetailScreen(
    viewModel: ChannelDetailViewModel = hiltViewModel(),
    channelId: String,
    onPostClick: (String) -> Unit,
    onUserClick: (String) -> Unit,
    onChannelClick: (String) -> Unit,
    onNavigateToManage: (String) -> Unit,
    onNavigateToWrite: (String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.container.stateFlow.collectAsState()

    LaunchedEffect(Unit) {
//        viewModel.loadChannel(channelId)
    }

    LaunchedEffect(Unit) {
        viewModel.container.sideEffectFlow.collectLatest { sideEffect ->
            when (sideEffect) {
                is ChannelDetailSideEffect.ShowToast -> {
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                }
                is ChannelDetailSideEffect.NavigateToManageScreen -> onNavigateToManage("")
                is ChannelDetailSideEffect.NavigateToWriteScreen -> state.channel?.id?.let { onNavigateToWrite(it) }
//                is ChannelDetailSideEffect.ShowSubscribeDialog -> viewModel.confirmSubscribe()
//                is ChannelDetailSideEffect.ShowUnsubscribeDialog -> viewModel.confirmUnsubscribe()
                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Paddings.layout_horizontal, vertical = Paddings.layout_vertical)
    ) {
        state.channel?.let { channel ->
            ChannelHeader(channel)

            Spacer(modifier = Modifier.height(Paddings.medium))

//            state.posts.forEach { post ->
//                PostPreviewCard(post = post)
//            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }

    if (state.channel != null && state.isPostButtonVisible) {
        TebahTextFieldButton(
            hint = "\"${state.channel!!.name}\"에 글쓰기",
            onClick = { onNavigateToWrite(state.channel!!.id) }
        )
    }
}

@Composable
fun ConfirmDialog(
    title: String,
    description: String,
    confirmText: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(text = title, style = TebahTypography.titleMedium) },
        text = { Text(text = description, style = TebahTypography.bodyMedium) },
        confirmButton = {
            MediumButton(text = confirmText, onClick = onConfirm)
        },
        dismissButton = {
            MediumButton(text = "취소", onClick = onCancel)
        }
    )
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
    return sdf.format(Date(timestamp))
}

@Composable
fun ChannelHeader(channel: Channel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Paddings.large)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = channel.name,
                style = TebahTypography.headlineSmall
            )

            if (channel.isOfficial) {
                Spacer(modifier = Modifier.width(Paddings.small))
                Text(
                    text = "공식",
                    style = TebahTypography.labelSmall,
                    color = Color.White,
                    modifier = Modifier
                        .background(Color.Blue, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }

        channel.description?.let {
            Spacer(modifier = Modifier.height(Paddings.medium))
            Text(
                text = it,
                style = TebahTypography.bodyMedium,
                color = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.height(Paddings.small))
        Text(
            text = "생성일: ${formatDate(channel.createdAt)}",
            style = TebahTypography.labelSmall,
            color = Color.Gray
        )
    }
}

@Composable
fun TebahTextFieldButton(
    hint: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .clickable { onClick() }
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = hint,
            style = TebahTypography.bodyMedium,
            color = Color.DarkGray
        )
    }
}