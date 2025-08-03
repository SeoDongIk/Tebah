package com.example.presentation.member.screen.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.presentation.R
import com.example.presentation.member.screen.MemberNavigator

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    listState: LazyListState = rememberLazyListState(),
    navigator: MemberNavigator
) {
    val pullOffset = listState.firstVisibleItemScrollOffset
    val pullProgress = remember(pullOffset) {
        derivedStateOf {
            val progress = (200 - pullOffset).coerceIn(0, 200) / 200f
            progress
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(state = listState) {
            item {
                // 상단 로고 + 소개 박스
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, bottom = 16.dp)
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val logoScale by animateFloatAsState(
                        targetValue = 1f + (pullProgress.value * 0.2f),
                        label = "LogoScale"
                    )

                    Image(
                        painter = painterResource(id = R.drawable.tebah_logo),
                        contentDescription = "Teboh Logo",
                        modifier = Modifier
                            .height(48.dp)
                            .scale(logoScale)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 교회 소개 이미지
                    Image(
                        painter = painterResource(id = R.drawable.church_intro_banner),
                        contentDescription = "교회 소개",
                        modifier = Modifier
                            .width(344.dp)
                            .height(120.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("추천", style = MaterialTheme.typography.bodyLarge, color = Color.Black)
                    Text("공지", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
                }
            }

            // 콘텐츠 영역 - 항상 보여야 함
            items((1..20).toList()) { index ->
//                PostPreviewCard(
//                    title = "게시글 제목 $index",
//                    content = "게시글 내용 미리보기",
//                    onClick = {}
//                )
            }
        }
    }
}
