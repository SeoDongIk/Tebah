package com.example.presentation.member.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.presentation.member.screen.home.HomeScreen
import com.example.presentation.member.screen.search.DiscoverScreen
import com.example.presentation.member.screen.search.SearchPromptScreen
import com.example.presentation.member.screen.search.SearchResultScreen
import com.example.presentation.shared.feature.channel.screen.ChannelCreateScreen
import com.example.presentation.shared.feature.channel.screen.ChannelDetailScreen
import com.example.presentation.shared.feature.notification.screen.NotificationScreen
import com.example.presentation.shared.feature.post.screen.PhotoDetailScreen
import com.example.presentation.shared.feature.post.screen.PostDetailScreen
import com.example.presentation.shared.feature.user.screen.FollowListScreen
import com.example.presentation.shared.feature.user.screen.FollowSuggestionScreen
import com.example.presentation.shared.feature.user.screen.OtherUserScreen
import com.example.presentation.shared.feature.user.screen.ProfileEditScreen
import com.example.presentation.shared.feature.user.screen.UserScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.max

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MemberNavHost(onNavigateToPostWrite: (Context) -> Unit) {
    val context = LocalContext.current

    val rootNavController = rememberNavController()
    val homeNavController = rememberNavController()
    val searchNavController = rememberNavController()
    val notificationNavController = rememberNavController()
    val userNavController = rememberNavController()

    val navigator = remember(rootNavController) { MemberNavigator(rootNavController) }
    val rootBackStackEntry by rootNavController.currentBackStackEntryAsState()
    var selectedTab by rememberSaveable { mutableStateOf(MemberTabRoute.HOME) }
    val tabHistory = remember { mutableStateListOf(MemberTabRoute.HOME) }

    val homeListState = rememberLazyListState()
    val searchListState = rememberLazyListState()
    val notifListState = rememberLazyListState()
    val userListState = rememberLazyListState()

    val headerBaseHeight: Dp = 150.dp
    val headerMaxExtra: Dp = 120.dp
    val fixedBoxHeight: Dp = 96.dp
    var pullOffsetPx by remember { mutableFloatStateOf(0f) }
    var isPulling by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val maxPullPx = with(density) { headerMaxExtra.toPx() }
    fun isAtTop(state: LazyListState): Boolean =
        state.firstVisibleItemIndex == 0 && state.firstVisibleItemScrollOffset == 0

    val triggerPx = maxPullPx * 0.6f
    val refreshHoldMillis = 1200L
    var refreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    var isScrollingDown by remember { mutableStateOf(false) }
    var targetScrollingDown by remember { mutableStateOf(false) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                targetScrollingDown = available.y < 0
                if (refreshing) return Offset(x = 0f, y = available.y)
                if (source == NestedScrollSource.Drag) {
                    val dy = available.y
                    if (dy > 0f && isAtTop(homeListState)) {
                        val new = (pullOffsetPx + dy).coerceIn(0f, maxPullPx)
                        val consumedY = new - pullOffsetPx
                        if (consumedY != 0f) {
                            pullOffsetPx = new
                            isPulling = true
                            return Offset(x = 0f, y = consumedY)
                        }
                    }
                }
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (refreshing) return Offset.Zero
                if (source != NestedScrollSource.Drag) return Offset.Zero

                val dy = available.y

                if (dy > 0f && isAtTop(homeListState)) {
                    val new = (pullOffsetPx + dy).coerceIn(0f, maxPullPx)
                    val consumedY = new - pullOffsetPx
                    if (consumedY != 0f) {
                        pullOffsetPx = new
                        isPulling = true
                        return Offset(x = 0f, y = consumedY)
                    }
                }

                if (dy < 0f && pullOffsetPx > 0f) {
                    val new = max(0f, pullOffsetPx + dy)
                    val consumedY = new - pullOffsetPx
                    if (consumedY != 0f) {
                        pullOffsetPx = new
                        isPulling = true
                        return Offset(x = 0f, y = consumedY)
                    }
                }
                return Offset.Zero
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                val shouldTrigger = pullOffsetPx >= triggerPx && !refreshing
                isPulling = false

                if (shouldTrigger) {
                    refreshing = true
                    pullOffsetPx = 0f
                    scope.launch {
                        // TODO: 실제 새로고침 로직 호출
                        delay(refreshHoldMillis)
                        refreshing = false
                    }
                } else {
                    if (pullOffsetPx > 0f) pullOffsetPx = 0f
                }
                return Velocity.Zero
            }
        }
    }

    val bottomBarVisible = rootBackStackEntry?.destination?.route == "empty"
    val bottomBarOffset = remember { Animatable(0f) }

    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels /
            LocalContext.current.resources.displayMetrics.density

    val tabOrder = remember {
        listOf(
            MemberTabRoute.HOME,
            MemberTabRoute.SEARCH,
            MemberTabRoute.NOTIFICATION,
            MemberTabRoute.USER
        )
    }
    fun tabOffsetDp(tab: MemberTabRoute): Dp {
        val sel = tabOrder.indexOf(selectedTab)
        val idx = tabOrder.indexOf(tab)
        return when {
            idx == sel -> 0.dp
            idx < sel  -> (-screenWidth).dp
            else       -> (screenWidth).dp
        }
    }

    LaunchedEffect(targetScrollingDown) {
        kotlinx.coroutines.delay(200)
        isScrollingDown = targetScrollingDown
    }
    LaunchedEffect(bottomBarVisible, isScrollingDown) {
        val target = if (bottomBarVisible && isScrollingDown) 80f else 0f
        bottomBarOffset.animateTo(target, animationSpec = tween(300))
    }

    BackHandler {
        val currentNavController = when (selectedTab) {
            MemberTabRoute.HOME -> homeNavController
            MemberTabRoute.SEARCH -> searchNavController
            MemberTabRoute.NOTIFICATION -> notificationNavController
            MemberTabRoute.USER -> userNavController
            else -> homeNavController
        }
        if (!currentNavController.popBackStack()) {
            if (selectedTab == MemberTabRoute.HOME) {
                (context as? Activity)?.finish()
            } else if (tabHistory.size > 1) {
                tabHistory.removeAt(tabHistory.lastIndex)
                selectedTab = tabHistory.last()
            } else {
                (context as? Activity)?.finish()
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(nestedScrollConnection),
        bottomBar = {
            AnimatedVisibility(visible = bottomBarVisible, modifier = Modifier.zIndex(2f)) {
                Box(
                    modifier = Modifier.graphicsLayer {
                        translationY = bottomBarOffset.value.dp.toPx()
                    }
                ) {
                    MemberBottomBar(
                        currentRoute = selectedTab.route,
                        onItemSelected = { tab ->
                            if (selectedTab == tab) {
                                val controller = when (tab) {
                                    MemberTabRoute.HOME -> homeNavController
                                    MemberTabRoute.SEARCH -> searchNavController
                                    MemberTabRoute.NOTIFICATION -> notificationNavController
                                    MemberTabRoute.USER -> userNavController
                                    else -> homeNavController
                                }
                                controller.popBackStack(tab.route, inclusive = false)
                            } else {
                                if (tabHistory.last() != tab) {
                                    tabHistory.remove(tab)
                                    tabHistory.add(tab)
                                }
                                selectedTab = tab
                            }
                        },
                        onWriteClick = { onNavigateToPostWrite(context) }
                    )
                }
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = bottomBarVisible && selectedTab == MemberTabRoute.HOME && isScrollingDown,
                enter = scaleIn(initialScale = 0.8f) + fadeIn(animationSpec = tween(300)),
                exit = scaleOut(targetScale = 0.8f) + fadeOut(animationSpec = tween(300))
            ) {
                FloatingActionButton(
                    onClick = { onNavigateToPostWrite(context) },
                    modifier = Modifier.zIndex(1f),
                    containerColor =  MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.EndOverlay
    ) { _ ->
        Box {
            Box(modifier = Modifier.offset(x = tabOffsetDp(MemberTabRoute.HOME))) {
                TabNavHost(
                    navController = homeNavController,
                    startRoute = MemberTabRoute.HOME.route
                ) {
                    HomeScreen(
                        listState = homeListState,
                        pullOffsetPx = pullOffsetPx,
                        isPulling = isPulling,
                        refreshing = refreshing,
                        armed = pullOffsetPx >= triggerPx && isPulling && !refreshing,
                        headerBaseHeight = headerBaseHeight,
                        headerMaxExtra = headerMaxExtra,
                        fixedBoxHeight = fixedBoxHeight
                    )
                }
            }

            Box(modifier = Modifier.offset(x = tabOffsetDp(MemberTabRoute.SEARCH))) {
                SearchNavHost(
                    navController = searchNavController,
                    listState = searchListState,
                    navigator = navigator
                )
            }

            Box(modifier = Modifier.offset(x = tabOffsetDp(MemberTabRoute.NOTIFICATION))) {
                TabNavHost(
                    navController = notificationNavController,
                    startRoute = MemberTabRoute.NOTIFICATION.route
                ) {
                    NotificationScreen(
                        listState = notifListState,
                        navigator = navigator
                    )
                }
            }

            Box(modifier = Modifier.offset(x = tabOffsetDp(MemberTabRoute.USER))) {
                TabNavHost(
                    navController = userNavController,
                    startRoute = MemberTabRoute.USER.route
                ) {
                    UserScreen(
                        listState = userListState,
                        navigator = navigator
                    )
                }
            }
        }

        NavHost(
            navController = rootNavController,
            startDestination = "empty",
            modifier = Modifier
        ) {
            composable("empty") { }
            composable(MemberRoute.PostDetail.route) {
                val postId = it.arguments?.getString("postId") ?: ""
                PostDetailScreen(postId = postId, navigator = navigator)
            }
            composable(MemberRoute.ChannelDetail.route) {
                val channelId = it.arguments?.getString("channelId") ?: ""
                ChannelDetailScreen(
                    channelId = channelId,
                    navigator = navigator,
                    onNavigateToWrite = onNavigateToPostWrite,
                )
            }
            composable(MemberRoute.PhotoDetail.route) {
                val photoUrl = it.arguments?.getString("photoUrl") ?: ""
                PhotoDetailScreen(photoUrl = photoUrl, navigator = navigator)
            }
            composable(MemberRoute.OtherUser.route) {
                val userId = it.arguments?.getString("userId") ?: ""
                OtherUserScreen(userId = userId, navigator = navigator)
            }
            composable(MemberRoute.ProfileEdit.route) {
                ProfileEditScreen(navigator = navigator)
            }
            composable(MemberRoute.FollowList.route) {
                FollowListScreen(navigator = navigator)
            }
            composable(MemberRoute.ChannelCreate.route) {
                ChannelCreateScreen(navigator = navigator)
            }
            composable(MemberRoute.FollowSuggestion.route) {
                FollowSuggestionScreen(navigator = navigator)
            }
        }
    }
}

@Composable
private fun TabNavHost(
    navController: NavHostController,
    startRoute: String,
    content: @Composable () -> Unit
) {
    NavHost(navController = navController, startDestination = startRoute) {
        composable(startRoute) { content() }
    }
}

@Composable
fun SearchNavHost(
    navController: NavHostController,
    listState: LazyListState,
    navigator: MemberNavigator
) {
    NavHost(navController = navController, startDestination = "searchMain") {
        composable("searchMain") {
            DiscoverScreen(
                listState = listState,
                navigator = navigator,
                onNavigateToSearchDetail = {
                    navController.navigate("searchPrompt")
                }
            )
        }
        composable("searchPrompt") {
            SearchPromptScreen(
                onKeywordSelected = { keyword ->
                    navController.navigate("searchResult/$keyword")
                }
            )
        }
        composable("searchResult/{keyword}") { backStackEntry ->
            val keyword = backStackEntry.arguments?.getString("keyword") ?: ""
            SearchResultScreen(
                keyword = keyword,
                navigator = navigator
            )
        }
    }
}