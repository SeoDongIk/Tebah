package com.example.presentation.auth.screen

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.presentation.auth.viewmodel.AdminSignUpViewModel
import com.example.presentation.auth.viewmodel.MemberSignUpViewModel

private const val TRANSITION_DURATION = 300

sealed class AuthRoute(val route: String) {
    object Login : AuthRoute("login")
    object Role : AuthRoute("role")
    object MemberInfo : AuthRoute("memberinfo")
    object ChurchSelect : AuthRoute("churchselect")
    object ChurchInfo : AuthRoute("churchinfo")
    object AdminInfo : AuthRoute("admininfo")
    object Complete : AuthRoute("complete")
}

private fun slideIn(offset: Int) = slideInHorizontally(
    initialOffsetX = { offset },
    animationSpec = tween(TRANSITION_DURATION)
)
private fun slideOut(offset: Int) = slideOutHorizontally(
    targetOffsetX = { offset },
    animationSpec = tween(TRANSITION_DURATION)
)
private fun slideInVertical(offset: Int) = slideInVertically(
    initialOffsetY = { offset },
    animationSpec = tween(TRANSITION_DURATION)
)
private fun slideOutVertical(offset: Int) = slideOutVertically(
    targetOffsetY = { offset },
    animationSpec = tween(TRANSITION_DURATION)
)
private val fadeInAnim = fadeIn(animationSpec = tween(TRANSITION_DURATION))
private val fadeOutAnim = fadeOut(animationSpec = tween(TRANSITION_DURATION))

private fun androidx.navigation.NavGraphBuilder.animatedComposable(
    route: String,
    enter: EnterTransition,
    exit: ExitTransition,
    popEnter: EnterTransition,
    popExit: ExitTransition,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route,
        enterTransition = { enter },
        exitTransition = { exit },
        popEnterTransition = { popEnter },
        popExitTransition = { popExit },
        content = content
    )
}

@Composable
fun AuthNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = AuthRoute.Login.route,
    ) {
        // 로그인
        animatedComposable(
            route = AuthRoute.Login.route,
            enter = slideInVertical(1000) + fadeInAnim,
            exit = slideOutVertical(1000) + fadeOutAnim,
            popEnter = slideInVertical(1000) + fadeInAnim,
            popExit = slideOutVertical(1000) + fadeOutAnim
        ) {
            SignInScreen(onNavigateToRole = { navController.navigate(AuthRoute.Role.route) })
        }

        // 역할 선택
        animatedComposable(
            route = AuthRoute.Role.route,
            enter = slideInVertical(-1000) + fadeInAnim,
            exit = slideOut(-1000) + fadeOutAnim,
            popEnter = slideIn(-1000) + fadeInAnim,
            popExit = slideOutVertical(-1000) + fadeOutAnim
        ) {
            RoleScreen(
                onNavigateToMemberInfo = { navController.navigate(AuthRoute.MemberInfo.route) },
                onNavigateToChurchInfo = { navController.navigate(AuthRoute.ChurchInfo.route) },
                onBackClick = { navController.popBackStack() }
            )
        }

        // 멤버 정보 입력
        animatedComposable(
            route = AuthRoute.MemberInfo.route,
            enter = slideIn(1000) + fadeInAnim,
            exit = slideOut(-1000) + fadeOutAnim,
            popEnter = slideIn(-1000) + fadeInAnim,
            popExit = slideOut(1000) + fadeOutAnim
        ) { backStackEntry ->
            val viewModel: MemberSignUpViewModel = hiltViewModel(backStackEntry)
            MemberInfoScreen(
                viewModel = viewModel,
                onNavigateToChurchSelect = { navController.navigate(AuthRoute.ChurchSelect.route) },
                onBackClick = { navController.popBackStack() }
            )
        }

        // 교회 선택
        animatedComposable(
            route = AuthRoute.ChurchSelect.route,
            enter = slideIn(1000) + fadeInAnim,
            exit = slideOut(-1000) + fadeOutAnim,
            popEnter = slideIn(-1000) + fadeInAnim,
            popExit = slideOut(1000) + fadeOutAnim
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(AuthRoute.MemberInfo.route)
            }
            val viewModel: MemberSignUpViewModel = hiltViewModel(parentEntry)
            ChurchSelectScreen(
                viewModel = viewModel,
                onNavigateToComplete = { navController.navigate(AuthRoute.Complete.route) },
                onBackClick = { navController.popBackStack() }
            )
        }

        // 교회 정보 입력 (관리자)
        animatedComposable(
            route = AuthRoute.ChurchInfo.route,
            enter = slideIn(1000) + fadeInAnim,
            exit = slideOut(-1000) + fadeOutAnim,
            popEnter = slideIn(-1000) + fadeInAnim,
            popExit = slideOut(1000) + fadeOutAnim
        ) { backStackEntry ->
            val viewModel: AdminSignUpViewModel = hiltViewModel(backStackEntry)
            ChurchInfoScreen(
                viewModel = viewModel,
                onNavigateToAdminInfo = { navController.navigate(AuthRoute.AdminInfo.route) },
                onBackClick = { navController.popBackStack() }
            )
        }

        // 관리자 정보 입력
        animatedComposable(
            route = AuthRoute.AdminInfo.route,
            enter = slideIn(1000) + fadeInAnim,
            exit = slideOut(-1000) + fadeOutAnim,
            popEnter = slideIn(-1000) + fadeInAnim,
            popExit = slideOut(1000) + fadeOutAnim
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(AuthRoute.ChurchInfo.route)
            }
            val viewModel: AdminSignUpViewModel = hiltViewModel(parentEntry)
            AdminInfoScreen(
                viewModel = viewModel,
                onNavigateToComplete = { navController.navigate(AuthRoute.Complete.route) },
                onBackClick = { navController.popBackStack() }
            )
        }

        // 완료 화면
        animatedComposable(
            route = AuthRoute.Complete.route,
            enter = fadeInAnim + slideInVertical(1000),
            exit = fadeOutAnim,
            popEnter = fadeInAnim + slideInVertical(1000),
            popExit = fadeOutAnim
        ) {
            val previousEntry = navController.previousBackStackEntry
            val viewModel: ViewModel? = previousEntry?.let {
                when (it.destination.route) {
                    AuthRoute.MemberInfo.route,
                    AuthRoute.ChurchSelect.route ->  {
                        val memberEntry = navController.getBackStackEntry(AuthRoute.MemberInfo.route)
                        hiltViewModel<MemberSignUpViewModel>(memberEntry)
                    }
                    AuthRoute.ChurchInfo.route,
                    AuthRoute.AdminInfo.route -> {
                        val adminEntry = navController.getBackStackEntry(AuthRoute.ChurchInfo.route)
                        hiltViewModel<AdminSignUpViewModel>(adminEntry)
                    }
                    else -> null
                }
            }

            viewModel?.let {
                when (it) {
                    is MemberSignUpViewModel -> CompleteScreen(viewModel = it)
                    is AdminSignUpViewModel -> CompleteScreen(viewModel = it)
                }
            } ?: Text("화면 전환 오류: ViewModel을 찾을 수 없습니다.")
        }
    }
}