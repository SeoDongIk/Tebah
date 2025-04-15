package com.example.presentation.main.home

import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTest(
    onFabClick: () -> Unit,
    showBottomBar: (Boolean) -> Unit
) {
    val refreshing = remember { mutableStateOf(false) }
    val overScrollOffset = remember { mutableFloatStateOf(0f) }

    val density = LocalDensity.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val listState = rememberLazyListState()
    val isScrolling = listState.isScrollInProgress

    // TopBar 애니메이션용 상태
    val topBarProgress by remember {
        derivedStateOf {
            val scrollOffset = listState.firstVisibleItemScrollOffset.coerceIn(0, 300)
            1f - (scrollOffset / 300f)
        }
    }

    // 새로고침 조건 처리
    LaunchedEffect(refreshing.value) {
        if (refreshing.value) {
            delay(1500) // 예시용 새로고침 딜레이
            refreshing.value = false
        }
    }
    var refreshHeight by remember { mutableStateOf(0.dp) }

    // Lottie 애니메이션 구성
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("refresh.json"))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = overScrollOffset.floatValue > 3f || refreshing.value,
        iterations = LottieConstants.IterateForever
    )

    // NestedScroll 로직
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset = Offset.Zero

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset <= 10 && available.y > 0f) {
                    // 위에서 당기기 중일 때만 적용
                    val factor = 30f
                    val newOffset = (overScrollOffset.floatValue + available.y*factor)
                        .coerceAtLeast(0f)
                        .coerceAtMost(250f) // 최대 당김 범위
                    overScrollOffset.floatValue = newOffset
                }
                return Offset.Zero
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                if (overScrollOffset.floatValue > 100f && !refreshing.value) {
                    refreshing.value = true
                }
                return Velocity.Zero
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                overScrollOffset.floatValue = 0f
                return super.onPostFling(consumed, available)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        val animatedOffset by animateFloatAsState(
            targetValue = overScrollOffset.floatValue,
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            ),
            label = "overscroll"
        )

        LaunchedEffect(refreshing.value == true && isScrolling) {
            if (refreshing.value && !isScrolling) {
                refreshHeight = with(density) { (animatedOffset / 1.5f).toDp() }
            }

            // refreshing 끝났으면 초기화
            if (!refreshing.value) {
                refreshHeight = 0.dp
            }
        }

        val targetHeight = if (refreshing.value && refreshHeight > 0.dp) {
            refreshHeight
        } else {
            with(density) { (animatedOffset / 1.5f).toDp() }
        }

        val animatedHeight by animateDpAsState(
            targetValue = targetHeight,
            animationSpec = tween(300),
            label = "refreshBoxHeight"
        )
        // 오버 스크롤 영역에 표시될 Lottie or Animation
        if (overScrollOffset.floatValue > 0f || refreshing.value) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(animatedHeight),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LottieAnimation(
                        composition = composition,
                        progress = { progress },
                        modifier = Modifier.size(80.dp)
                    )
                    Text(
                        if (refreshing.value) "새로고침 중..." else "🔄 새로고침 당기는 중...",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 16.sp
                    )
                }
            }
        }

        Column {


            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(nestedScrollConnection)
            ) {
                item {
                    // 상단바 애니메이션
                    val animatedAlpha by animateFloatAsState(
                        targetValue = topBarProgress,
                        animationSpec = tween(200),
                        label = "alpha"
                    )
                    val animatedHeight by animateDpAsState(
                        targetValue = (topBarProgress * 56).dp,
                        animationSpec = tween(200),
                        label = "height"
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(animatedHeight)
                            .background(MaterialTheme.colorScheme.primary)
                            .alpha(animatedAlpha),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "스크롤되면 점점 나타나는 상단 바",
                            color = Color.White,
                            modifier = Modifier.alpha(animatedAlpha)
                        )
                    }
                }
                stickyHeader {
                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        tonalElevation = 4.dp
                    ) {
                        Text(
                            "📌 Sticky Header",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                items(30) { index ->
                    Text(
                        "Item #$index",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }

}