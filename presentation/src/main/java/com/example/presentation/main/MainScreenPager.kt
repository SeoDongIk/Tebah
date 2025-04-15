package com.example.presentation.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.presentation.main.home.HomeScreen
import com.example.presentation.main.home.HomeScreenTest
import com.example.presentation.main.like.LikeScreen
import com.example.presentation.main.search.SearchScreen
import com.example.presentation.main.user.UserScreen
import com.example.presentation.main.write.WriteScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenPager(
    currentScreen: MainRoute,
    onFabClick: () -> Unit,
    showBottomBar: (Boolean) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 5 }
    )

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = false
    ) { page ->
        when (page) {
            0 -> HomeScreenTest(
                onFabClick = onFabClick,
                showBottomBar = showBottomBar
            )
            1 -> SearchScreen()
            2 -> WriteScreen()
            3 -> LikeScreen()
            4 -> UserScreen()
        }
    }

    LaunchedEffect(currentScreen) {
        pagerState.animateScrollToPage(
            when (currentScreen) {
                MainRoute.HOME -> 0
                MainRoute.SEARCH -> 1
                MainRoute.WRITE -> 2
                MainRoute.LIKE -> 3
                MainRoute.USER -> 4
            }
        )
    }
}