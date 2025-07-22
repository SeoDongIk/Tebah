package com.example.presentation.member

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.example.presentation.common.theme.TebahTheme
import com.example.presentation.member.screen.MemberNavHost
import com.example.presentation.write.WriteActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val window = window
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        window.navigationBarColor = Color.White.toArgb()
        controller.isAppearanceLightNavigationBars = true

        setContent {
            TebahTheme {
                MemberNavHost(
                    onNavigateToPostWrite = { context -> launchWriteActivity(context) }
                )
            }
        }
    }
}

fun launchWriteActivity(context: Context) {
    val intent = Intent(context, WriteActivity::class.java)
    context.startActivity(intent)
}