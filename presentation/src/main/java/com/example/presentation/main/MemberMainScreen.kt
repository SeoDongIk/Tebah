package com.example.presentation.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.main.member.write.WriteActivity
import com.example.presentation.theme.Paddings
import com.example.presentation.theme.TebahTheme
import com.example.presentation.theme.primary
import com.example.presentation.theme.secondary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MemberMainScreen(
    onNavigateToSearchDetail: () -> Unit
) {
    val context = LocalContext.current
    var currentRoute by rememberSaveable { mutableStateOf(MainRoute.HOME) }
    var bottomBarState by remember { mutableStateOf(true) }

    val transition =  updateTransition(targetState = bottomBarState, label = "BottomBarTransition")

    val fabScale by transition.animateFloat(
        transitionSpec = {
            tween(300)
        }, label = "FabScale"
    ) { visible ->
        if(visible) 0f else 1f
    }

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullToRefreshState()
    val pullProgress = pullRefreshState.distanceFraction

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
                MainScreenPager(currentRoute, pullProgress, onNavigateToSearchDetail)
            }
        }

        AnimatedVisibility(
            visible = bottomBarState,
            enter = slideInVertically { it },
            exit = slideOutVertically { it },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            MainBottomNavigationBar(
                currentRoute = currentRoute,
                onItemSelected = { selectedRoute ->
                    if(selectedRoute != MainRoute.WRITE) {
                        currentRoute = selectedRoute
                    } else {
                        launchWriteActivity(context)
                    }
                }
            )
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

fun launchWriteActivity(context: Context) {
    val intent = Intent(context, WriteActivity::class.java)
    context.startActivity(intent)
}

//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview() {
//    TebahTheme {
//        MemberMainScreen()
//    }
//}