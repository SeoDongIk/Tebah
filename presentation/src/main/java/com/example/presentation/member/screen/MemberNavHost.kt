package com.example.presentation.member.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.presentation.common.theme.third_01
import com.example.presentation.member.screen.home.HomeScreen
import com.example.presentation.member.screen.search.DiscoverScreen
import com.example.presentation.member.screen.search.SearchPromptScreen
import com.example.presentation.member.screen.search.SearchResultScreen
import com.example.presentation.shared.component.PhotoDetailScreen
import com.example.presentation.shared.feature.channel.screen.ChannelCreateScreen
import com.example.presentation.shared.feature.channel.screen.ChannelDetailScreen
import com.example.presentation.shared.feature.notification.screen.NotificationScreen
import com.example.presentation.shared.feature.post.screen.PostDetailScreen
import com.example.presentation.shared.feature.user.screen.FollowListScreen
import com.example.presentation.shared.feature.user.screen.FollowSuggestionScreen
import com.example.presentation.shared.feature.user.screen.OtherUserScreen
import com.example.presentation.shared.feature.user.screen.ProfileEditScreen
import com.example.presentation.shared.feature.user.screen.UserScreen
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MemberNavHost(onNavigateToPostWrite: (Context) -> Unit) {
    val context = LocalContext.current

    val rootNavController = rememberNavController()
    val homeNavController = rememberNavController()
    val searchNavController = rememberNavController()
    val notifNavController = rememberNavController()
    val userNavController = rememberNavController()

    val navigator = remember(rootNavController) { MemberNavigator(rootNavController) }
    val rootBackStackEntry by rootNavController.currentBackStackEntryAsState()
    var selectedTab by rememberSaveable { mutableStateOf(MemberTabRoute.HOME) }
    val tabHistory = remember { mutableStateListOf(MemberTabRoute.HOME) }

    val homeListState = rememberLazyListState()
    val searchListState = rememberLazyListState()
    val notifListState = rememberLazyListState()
    val userListState = rememberLazyListState()

    var isScrollingDown by remember { mutableStateOf(false) }
    var targetScrollingDown by remember { mutableStateOf(false) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                targetScrollingDown = available.y < 0
                return Offset.Zero
            }
        }
    }

    val bottomBarVisible = rootBackStackEntry?.destination?.route == "empty"
    val bottomBarOffset = remember { Animatable(0f) }

    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels / LocalContext.current.resources.displayMetrics.density
    val tabs = listOf(
        MemberTabRoute.HOME,
        MemberTabRoute.SEARCH,
        MemberTabRoute.NOTIFICATION,
        MemberTabRoute.USER
    )
    val offsets = tabs.map { tab ->
        animateDpAsState(
            targetValue = when (tab) {
                selectedTab -> 0.dp
                else -> if (tabs.indexOf(tab) < tabs.indexOf(selectedTab)) -screenWidth.dp else screenWidth.dp
            },
            animationSpec = tween(durationMillis = 250)
        )
    }

    LaunchedEffect(targetScrollingDown) {
        delay(200)
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
            MemberTabRoute.NOTIFICATION -> notifNavController
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
                                    MemberTabRoute.NOTIFICATION -> notifNavController
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
                    containerColor = third_01
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.EndOverlay
    ) { innerPadding ->
        Box {
            Box(modifier = Modifier.offset(x = offsets[0].value)) {
                TabNavHost(
                    navController = homeNavController,
                    startRoute = MemberTabRoute.HOME.route
                ) {
                    HomeScreen(
                        listState = homeListState,
                        onPostClick = navigator::onPostClick,
                        onUserClick = navigator::onUserClick,
                        onChannelClick = navigator::onChannelClick
                    )
                }
            }

            Box(modifier = Modifier.offset(x = offsets[1].value)) {
                SearchNavHost(
                    navController = searchNavController,
                    listState = searchListState,
                    navigator = navigator
                )
            }

            Box(modifier = Modifier.offset(x = offsets[2].value)) {
                TabNavHost(
                    navController = notifNavController,
                    startRoute = MemberTabRoute.NOTIFICATION.route
                ) {
                    NotificationScreen(
                        listState = notifListState,
                        onPostClick = navigator::onPostClick,
                        onUserClick = navigator::onUserClick,
                        onChannelClick = navigator::onChannelClick
                    )
                }
            }

            Box(modifier = Modifier.offset(x = offsets[3].value)) {
                TabNavHost(
                    navController = userNavController,
                    startRoute = MemberTabRoute.USER.route
                ) {
                    UserScreen(
                        listState = userListState,
                        onPostClick = navigator::onPostClick,
                        onUserClick = navigator::onUserClick,
                        onChannelClick = navigator::onChannelClick,
                        onPhotoClick = navigator::onNavigateToPhotoDetail,
                        onEditProfile = navigator::onNavigateToProfileEdit
                    )
                }
            }
        }

        NavHost(
            navController = rootNavController,
            startDestination = "empty",
            modifier = Modifier
        ) {
            composable("empty") {  }
            composable(MemberRoute.PostDetail.route) {
                val postId = it.arguments?.getString("postId") ?: ""
                PostDetailScreen(
                    postId = postId,
                    onPostClick = navigator::onPostClick,
                    onUserClick = navigator::onUserClick,
                    onChannelClick = navigator::onChannelClick
                )
            }
            composable(MemberRoute.ChannelDetail.route) {
                val channelId = it.arguments?.getString("channelId") ?: ""
                ChannelDetailScreen(
                    channelId = channelId,
                    onPostClick = navigator::onPostClick,
                    onUserClick = navigator::onUserClick,
                    onChannelClick = navigator::onChannelClick,
                    onNavigateToManage = { },
                    onNavigateToWrite = { },
                    onBack = { }
                )
            }
            composable(MemberRoute.PhotoDetail.route) {
                val photoUrl = it.arguments?.getString("photoUrl") ?: ""
                PhotoDetailScreen(photoUrl)
            }
            composable(MemberRoute.OtherUser.route) {
                val userId = it.arguments?.getString("userId") ?: ""
                OtherUserScreen(
                    userId = userId,
                    onPostClick = navigator::onPostClick,
                    onUserClick = navigator::onUserClick,
                    onChannelClick = navigator::onChannelClick
                )
            }
            composable(MemberRoute.ProfileEdit.route) {
                ProfileEditScreen(
                    onBack = navigator::onBack
                )
            }
            composable(MemberRoute.FollowList.route) {
                FollowListScreen(
                    onUserClick = navigator::onUserClick,
                    onBack = navigator::onBack
                )
            }
            composable(MemberRoute.ChannelCreate.route) {
                ChannelCreateScreen(onBack = navigator::onBack)
            }
            composable(MemberRoute.FollowSuggestion.route) {
                FollowSuggestionScreen(onUserClick = navigator::onUserClick)
            }
            composable(MemberRoute.PhotoDetail.route) {
                val photoUrl = it.arguments?.getString("photoUrl") ?: ""
                PhotoDetailScreen(photoUrl)
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
                onPostClick = navigator::onPostClick,
                onUserClick = navigator::onUserClick,
                onChannelClick = navigator::onChannelClick,
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
                onPostClick = navigator::onPostClick,
                onUserClick = navigator::onUserClick,
                onChannelClick = navigator::onChannelClick
            )
        }
    }
}