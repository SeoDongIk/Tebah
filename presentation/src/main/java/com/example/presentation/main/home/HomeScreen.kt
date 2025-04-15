package com.example.presentation.main.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onFabClick: () -> Unit,
    showBottomBar: (Boolean) -> Unit
) {
// 스크롤 상태 관리
    val scrollState = rememberLazyListState()
    var lastScrollOffset by remember { mutableStateOf(0) } // 마지막 스크롤 오프셋
    var scrollDirection by remember { mutableStateOf(0) } // 스크롤 방향 (1: 아래로, -1: 위로, 0: 없음)
    val previousScrollOffset = remember { mutableStateOf(0) }
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope() // ✅ 여기에 정의
    val topBarMaxRevealOffset = 200f // 얼마나 스크롤하면 완전히 나타나는지(px)
    val topBarVisibilityProgress by remember {
        derivedStateOf {
            val offset = scrollState.firstVisibleItemScrollOffset.toFloat()
            val index = scrollState.firstVisibleItemIndex
            if (index > 0) 0f
            else 1f - (offset / topBarMaxRevealOffset).coerceIn(0f, 1f)
        }
    }

    LaunchedEffect(scrollState.firstVisibleItemIndex, scrollState.firstVisibleItemScrollOffset) {
        val currentOffset = scrollState.firstVisibleItemScrollOffset

        // 스크롤이 아래로 가는지 위로 가는지 판단
        scrollDirection = when {
            currentOffset > lastScrollOffset -> 1 // 아래로 스크롤
            currentOffset < lastScrollOffset -> -1 // 위로 스크롤
            else -> scrollDirection
        }

        // 스크롤 방향에 따라 BottomNavigationBar 상태 변경
        if (scrollDirection == 1) {
            showBottomBar(false) // 아래로 스크롤 시 BottomNavigationBar 숨김
        } else if (scrollDirection == -1) {
            showBottomBar(true) // 위로 스크롤 시 BottomNavigationBar 보임
        }

        lastScrollOffset = currentOffset
        previousScrollOffset.value = currentOffset

    }

    fun refresh() {
        isRefreshing = true
        scope.launch { // ✅ 기존의 CoroutineScope(Dispatchers.Main).launch 대신 사용
            delay(1500)
            isRefreshing = false
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { refresh() },
        indicator = { state, trigger ->
            if (state.isSwipeInProgress || isRefreshing) {
                LottieRefreshIndicator()
            }
        }
    ) {
        Column {
            val maxHeight = 500.dp
            val heightPx = with(LocalDensity.current) { maxHeight.toPx() }

            val animatedAlpha by animateFloatAsState(
                targetValue = topBarVisibilityProgress,
                animationSpec = tween(durationMillis = 200),
                label = "AlphaAnimation"
            )

            val animatedHeight by animateFloatAsState(
                targetValue = topBarVisibilityProgress * heightPx,
                animationSpec = tween(durationMillis = 200),
                label = "HeightAnimation"
            )


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(with(LocalDensity.current) { animatedHeight.toDp() })
                    .background(MaterialTheme.colorScheme.primary)
                    .alpha(animatedAlpha)
            ) {
                Text(
                    "스크롤되면 점점 나타나는 상단 바",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .alpha(animatedAlpha), // 텍스트 자체도 alpha 적용
                    color = Color.White
                )
            }

            LazyColumn(
                state = scrollState, // LazyColumn의 스크롤 상태를 연결
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // 🧲 Sticky Header (하단 일부 고정)
                stickyHeader {
                    StickyHeaderBar()
                }

                items(30) { index -> // 예시로 30개의 아이템 생성
                    ListItem(index)
                }
            }
        }

    }


}

@Composable
fun LottieRefreshIndicator() {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("refresh.json"))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        speed = 2f
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(60.dp)
        )
    }
}

@Composable
fun ListItem(index: Int) {
    // 각 리스트 아이템을 구성하는 컴포저블
    Text(
        text = "아이템 $index",
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp),
        style = MaterialTheme.typography.bodySmall
    )
}

@Composable
fun StickyHeaderBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        color = MaterialTheme.colorScheme.secondary,
        tonalElevation = 4.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text("Sticky Header", color = Color.White)
        }
    }
}