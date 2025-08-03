package com.example.presentation.shared.feature.channel.screen

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.presentation.common.theme.TebahTypography
import com.example.presentation.member.screen.MemberNavigator

@Composable
fun ChannelDetailScreen(
    channelId: String,
    navigator: MemberNavigator,
    onNavigateToWrite: (Context) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "📺 ChannelDetailScreen\nchannelId: $channelId",
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}