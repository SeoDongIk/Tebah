package com.example.presentation.admin.screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation.member.screen.MemberTabRoute
import com.example.presentation.common.theme.Paddings
import com.example.presentation.common.theme.primary
import com.example.presentation.common.theme.secondary
import com.example.presentation.common.theme.third_02
import com.example.presentation.common.theme.third_03
import com.example.presentation.member.launchWriteActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AdminMainScreen() {
    val context = LocalContext.current
    var currentRoute by remember { mutableStateOf(MemberTabRoute.HOME) }
    var bottomBarState by remember { mutableStateOf(true) }

    val transition =  updateTransition(targetState = bottomBarState, label = "BottomBarTransition")
    val fabScale by transition.animateFloat(
        transitionSpec = {
            tween(300)
        }, label = "FabScale"
    ) { visible ->
        if(visible) 0f else 1f
    }

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("팔로잉", "공지")

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullToRefreshState()
    val pullProgress = pullRefreshState.distanceFraction

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullToRefresh(
                isRefreshing = isRefreshing,
                onRefresh = {
                    // distanceFraction 값이 1.0 이상이 되었을 때 실행된다.
                    // isRefreshing 값이 true로 바뀐다.
                    isRefreshing = true
                    // 비동기 로딩 시작
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(1500)
                        isRefreshing = false
                    }
                },
                state = pullRefreshState
            )
    ) {
        Scaffold(
            contentWindowInsets = WindowInsets(0)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(400.dp))
                }
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.vector),
                            contentDescription = "로고 이미지",
                            tint = primary,
                            modifier = Modifier.size(48.dp + (pullProgress*50).dp)
                        )
                    }
                }

                stickyHeader {
                    Column(modifier = Modifier.background(Color.White)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            tabs.forEachIndexed { index, title ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { selectedTab = index }
                                        .fillMaxHeight(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = title,
                                        color = if (selectedTab == index) primary else third_03,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                        ) {
                            tabs.forEachIndexed { index, _ ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .align(Alignment.Bottom)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .background(if (selectedTab == index) third_02 else third_03)
                                            .align(Alignment.BottomCenter)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = bottomBarState,
            enter = slideInVertically { it },
            exit = slideOutVertically { it },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {

        }

        Box(
            modifier = Modifier
                .scale(fabScale)
                .alpha(fabScale)
                .align(Alignment.BottomEnd)
                .padding(Paddings.extra)
                .background(
                    color = primary,
                    shape = RoundedCornerShape(Paddings.xlarge)
                )
                .border(
                    width = Paddings.small,
                    color = secondary,
                    shape = RoundedCornerShape(Paddings.xlarge)
                )
                .clip(RoundedCornerShape(Paddings.xlarge))
                .clickable { launchWriteActivity(context) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "FAB",
                tint = Color.White,
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}