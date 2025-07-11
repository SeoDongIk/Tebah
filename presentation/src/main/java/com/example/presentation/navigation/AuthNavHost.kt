package com.example.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.presentation.auth.AdminSignUpViewModel
import com.example.presentation.auth.CompleteScreen
import com.example.presentation.auth.SignInScreen
import com.example.presentation.auth.MemberSignUpViewModel
import com.example.presentation.auth.RoleScreen
import com.example.presentation.auth.admin.AdminInfoScreen
import com.example.presentation.auth.admin.ChurchInfoScreen
import com.example.presentation.auth.member.ChurchSelectScreen
import com.example.presentation.auth.member.MemberInfoScreen

@Composable
fun AuthNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = "login",
    ) {
        val slideInFromRight = slideInHorizontally(
            initialOffsetX = { 1000 },
            animationSpec = tween(300)
        )
        val slideOutToLeft = slideOutHorizontally(
            targetOffsetX = { -1000 },
            animationSpec = tween(300)
        )
        val slideInFromLeft = slideInHorizontally(
            initialOffsetX = { -1000 },
            animationSpec = tween(300)
        )
        val slideOutToRight = slideOutHorizontally(
            targetOffsetX = { 1000 },
            animationSpec = tween(300)
        )

        val slideInFromBottom = slideInVertically(
            initialOffsetY = { 1000 },
            animationSpec = tween(300)
        )
        val slideOutToTop = slideOutVertically(
            targetOffsetY = { -1000 },
            animationSpec = tween(300)
        )
        val slideInFromTop = slideInVertically(
            initialOffsetY = { -1000 },
            animationSpec = tween(300)
        )
        val slideOutToBottom = slideOutVertically(
            targetOffsetY = { 1000 },
            animationSpec = tween(300)
        )

        val fadeInAnim = fadeIn(animationSpec = tween(300))
        val fadeOutAnim = fadeOut(animationSpec = tween(300))

        // 로그인 <-> RoleScreen : 수직 슬라이드
        composable("login",
            enterTransition = { slideInFromBottom + fadeInAnim },
            exitTransition = { slideOutToBottom + fadeOutAnim },
            popEnterTransition = { slideInFromBottom + fadeInAnim },
            popExitTransition = { slideOutToBottom + fadeOutAnim }
        ) {
            SignInScreen(onNavigateToRole = { navController.navigate("role") })
        }

        composable("role",
            enterTransition = { slideInFromTop + fadeInAnim },
            exitTransition = { slideOutToLeft + fadeOutAnim },
            popEnterTransition = { slideInFromLeft + fadeInAnim },
            popExitTransition = { slideOutToTop + fadeOutAnim }
        ) {
            RoleScreen(
                onNavigateToMemberInfo = { navController.navigate("memberinfo") },
                onNavigateToChurchInfo = { navController.navigate("churchinfo") }
            )
        }

        // RoleScreen <-> 다음 화면들 : 수평 슬라이드
        composable("memberinfo",
            enterTransition = { slideInFromRight + fadeInAnim },
            exitTransition = { slideOutToLeft + fadeOutAnim },
            popEnterTransition = { slideInFromLeft + fadeInAnim },
            popExitTransition = { slideOutToRight + fadeOutAnim }
        ) { backStackEntry ->
            val viewModel: MemberSignUpViewModel = hiltViewModel(backStackEntry)
            MemberInfoScreen(
                viewModel = viewModel,
                onNavigateToChurchSelect = { navController.navigate("churchselect") }
            )
        }

        composable("churchselect",
            enterTransition = { slideInFromRight + fadeInAnim },
            exitTransition = { slideOutToLeft + fadeOutAnim },
            popEnterTransition = { slideInFromLeft + fadeInAnim },
            popExitTransition = { slideOutToRight + fadeOutAnim }
        ) { backStackEntry ->
            val viewModel: MemberSignUpViewModel = hiltViewModel(backStackEntry)
            ChurchSelectScreen(
                viewModel = viewModel,
                onNavigateToComplete = { navController.navigate("complete") }
            )
        }

        composable("churchinfo",
            enterTransition = { slideInFromRight + fadeInAnim },
            exitTransition = { slideOutToLeft + fadeOutAnim },
            popEnterTransition = { slideInFromLeft + fadeInAnim },
            popExitTransition = { slideOutToRight + fadeOutAnim }
        ) { backStackEntry ->
            val viewModel: AdminSignUpViewModel = hiltViewModel(backStackEntry)
            ChurchInfoScreen(
                viewModel = viewModel,
                onNavigateToAdminInfo = { navController.navigate("admininfo") }
            )
        }

        composable("admininfo",
            enterTransition = { slideInFromRight + fadeInAnim },
            exitTransition = { slideOutToLeft + fadeOutAnim },
            popEnterTransition = { slideInFromLeft + fadeInAnim },
            popExitTransition = { slideOutToRight + fadeOutAnim }
        ) { backStackEntry ->
            val viewModel: AdminSignUpViewModel = hiltViewModel(backStackEntry)
            AdminInfoScreen(
                viewModel = viewModel,
                onNavigateToComplete = { navController.navigate("complete") }
            )
        }

        composable("complete",
            enterTransition = { fadeInAnim + slideInFromBottom },
            exitTransition = { fadeOutAnim },
            popEnterTransition = { fadeInAnim + slideInFromBottom },
            popExitTransition = { fadeOutAnim }
        ) { backStackEntry ->
            val previousEntry = navController.previousBackStackEntry
            val viewModel: ViewModel? = previousEntry?.let {
                when (it.destination.route) {
                    "memberinfo" -> hiltViewModel<MemberSignUpViewModel>(it)
                    "churchinfo" -> hiltViewModel<AdminSignUpViewModel>(it)
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