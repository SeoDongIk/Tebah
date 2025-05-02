package com.example.presentation.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.presentation.main.write.WriteActivity

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    var bottomBarState by remember { mutableStateOf(true) }
    var currentRoute by remember { mutableStateOf(MainRoute.HOME) }
    val context = LocalContext.current // remember 안하는게 맞음 ㅇㅇ.

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = bottomBarState,
                    enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight }),
                    exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight })
                ) {
                    MainBottomNavigationBar(
                        currentRoute = currentRoute,
                        onItemSelected = { selectedRoute ->
                            currentRoute = selectedRoute
                        }
                    )
                }
            },
            contentWindowInsets = WindowInsets(0)
        ) {
            MainScreenPager(
                onFabClick = {
                    val intent = Intent(context, WriteActivity::class.java)
                    context.startActivity(intent)
                },
                showBottomBar = { visible ->
                    bottomBarState = visible
                },
                currentScreen = currentRoute
            )
        }

        AnimatedVisibility(
            visible = !bottomBarState,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            FloatingActionButton(
                onClick = {
                    val intent = Intent(context, WriteActivity::class.java)
                    context.startActivity(intent)
                },
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "FAB"
                )
            }
        }
    }

}