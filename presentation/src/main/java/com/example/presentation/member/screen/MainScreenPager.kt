package com.example.presentation.member.screen

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.presentation.shared.feature.notification.screen.NotificationScreen
import com.example.presentation.member.screen.search.NewSearchScreen
import com.example.presentation.shared.feature.user.screen.UserScreen
import com.example.presentation.member.screen.home.HomeScreen
import com.example.presentation.write.WriteActivity
import com.example.presentation.member.state.MainRoute
import com.example.presentation.shared.feature.channel.screen.PreviewChannelDetailScreen

@Composable
fun MainScreenPager(
    currentScreen: MainRoute,
    pullProgress: Float,
    onNavigateToSearchDetail: () -> Unit
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 4 }
    )

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = false
    ) { page ->
        when (page) {
            0 -> HomeScreen(pullProgress)
            1 -> NewSearchScreen(pullProgress, onNavigateToSearchDetail)
            2 -> NotificationScreen(pullProgress)
            3 -> PreviewChannelDetailScreen()
        }
    }

    LaunchedEffect(currentScreen) {
        when (currentScreen) {
            MainRoute.HOME -> pagerState.scrollToPage(0)
            MainRoute.SEARCH -> pagerState.scrollToPage(1)
            MainRoute.LIKE -> pagerState.scrollToPage(2)
            MainRoute.USER -> pagerState.scrollToPage(3)
            MainRoute.WRITE -> {
                // 사실상 이동할 일이 없다고 봐야함.
                val intent = Intent(context, WriteActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}