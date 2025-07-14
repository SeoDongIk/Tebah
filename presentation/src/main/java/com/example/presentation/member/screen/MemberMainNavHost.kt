package com.example.presentation.member.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.presentation.member.screen.search.NewSearchScreenV2
import com.example.presentation.member.screen.search.NewSearchScreenV3

@Composable
fun MemberMainNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MemberMainScreen(
                onNavigateToSearchDetail = {
                    navController.navigate("searchV2")
                }
            )
        }
        composable("searchV2") {
            NewSearchScreenV2(
                onBackClick = {
                    navController.popBackStack()
                },
                onSearch = { query ->
                    navController.navigate("result/${query}")
                }
            )
        }
        composable("result/{query}") { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query")
            NewSearchScreenV3(
                query = query ?: "",
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}