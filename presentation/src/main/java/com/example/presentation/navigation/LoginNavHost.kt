package com.example.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.presentation.login.AdminSignUpViewModel
import com.example.presentation.login.CompleteScreen
import com.example.presentation.login.LoginScreen
import com.example.presentation.login.MemberSignUpViewModel
import com.example.presentation.login.RoleScreen
import com.example.presentation.login.admin.AdminInfoScreen
import com.example.presentation.login.admin.ChurchInfoScreen
import com.example.presentation.login.member.ChurchSelectScreen
import com.example.presentation.login.member.MemberInfoScreen

@Composable
fun LoginNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onNavigateToRole = {
                    navController.navigate("role")
                }
            )
        }
        composable("role") { backStackEntry ->
            RoleScreen(
                onNavigateToMemberInfo = {
                    navController.navigate("memberinfo")
                },
                onNavigateToChurchInfo = {
                    navController.navigate("churchinfo")
                }
            )
        }
        composable("memberinfo") {
            val parentEntry = remember { navController.getBackStackEntry("memberinfo") }
            val viewModel: MemberSignUpViewModel = hiltViewModel(parentEntry)
            MemberInfoScreen(
                viewModel = viewModel,
                onNavigateToChurchSelect = {
                    navController.navigate("churchselect")
                }
            )
        }
        composable("churchselect") {
            val parentEntry = remember { navController.getBackStackEntry("memberinfo") }
            val viewModel: MemberSignUpViewModel = hiltViewModel(parentEntry)
            ChurchSelectScreen(
                viewModel = viewModel,
                onNavigateToComplete = {
                    navController.navigate("complete")
                }
            )
        }
        composable("churchinfo") {
            val parentEntry = remember { navController.getBackStackEntry("churchinfo") }
            val viewModel: AdminSignUpViewModel = hiltViewModel(parentEntry)
            ChurchInfoScreen(
                viewModel = viewModel,
                onNavigateToAdminInfo = {
                    navController.navigate("admininfo")
                }
            )
        }
        composable("admininfo") {
            val parentEntry = remember { navController.getBackStackEntry("churchinfo") }
            val viewModel: AdminSignUpViewModel = hiltViewModel(parentEntry)
            AdminInfoScreen(
                viewModel = viewModel,
                onNavigateToComplete = {
                    navController.navigate("complete")
                }
            )
        }
        composable("complete") {
            val parentEntry = remember {
                navController.backQueue.findLast {
                    it.destination.route == "memberinfo" || it.destination.route == "churchinfo"
                }
            }

            val viewModel: ViewModel? = parentEntry?.let {
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
            } ?: run {
                Text("화면 전환 오류: ViewModel을 찾을 수 없습니다.")
            }
        }
    }
}