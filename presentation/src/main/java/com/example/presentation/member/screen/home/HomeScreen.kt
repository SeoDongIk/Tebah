package com.example.presentation.member.screen.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    listState: LazyListState,
    pullOffsetPx: Float,
    isPulling: Boolean,
    refreshing: Boolean,
    armed: Boolean,
    headerBaseHeight: Dp,
    headerMaxExtra: Dp,
    fixedBoxHeight: Dp
) {
    // 임시 리스트(나중에 뷰모델로 교체)
    val demoItems = remember { List(30) { "Item ${it + 1}" } }

    val density = LocalDensity.current
    val maxExtraPx = with(density) { headerMaxExtra.toPx() }

    // 헤더 추가 높이(px) 애니메이션
    val extraPxAnim = remember { Animatable(0f) }

    // 우선순위: refreshing > isPulling > idle
    LaunchedEffect(pullOffsetPx, isPulling, refreshing) {
        when {
            refreshing -> {
                // 새로고침 동안 헤더를 최대치까지 부드럽게 확장 유지
                extraPxAnim.animateTo(maxExtraPx, animationSpec = spring())
            }
            isPulling -> {
                val clamped = pullOffsetPx.coerceIn(0f, maxExtraPx)
                extraPxAnim.snapTo(clamped)
            }
            else -> {
                // 자연 복귀
                extraPxAnim.animateTo(0f, animationSpec = spring())
            } 
        }
    }

    val extraDp = with(density) { extraPxAnim.value.toDp() }
    val headerHeight = headerBaseHeight + extraDp

    // 상태에 따른 헤더 색/텍스트 (간단 피드백)
    val baseColor = Color(0xFF1C224E)
    val armedColor = Color(0xFF2E7D32)       // 초록: 놓으면 새로고침
    val refreshingColor = Color(0xFF1565C0)  // 파랑: 새로고침 중

    val headerBg = when {
        refreshing -> refreshingColor
        armed -> armedColor
        else -> baseColor
    }
    val headerLabel = when {
        refreshing -> "새로고침 중..."
        armed -> "놓으면 새로고침"
        else -> "헤더"
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // ① 오버스크롤로 커지는 헤더
        item(key = "stretchHeader") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(headerHeight)
                    .background(headerBg),
                contentAlignment = Alignment.Center
            ) {
                Text(text = headerLabel, color = Color.White)
            }
        }

        // ② 고정 박스(스티키 아님)
        item(key = "fixedBox") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(fixedBoxHeight)
                    .background(Color(0xFF263238)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "고정 박스", color = Color.White)
            }
        }

        // ③ 스티키 헤더
        stickyHeader(key = "stickyHeader") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color(0xFF455A64)),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "스티키 헤더",
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        // ④ 리스트
        items(demoItems, key = { it }) { label ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(text = label)
            }
        }
    }
}
