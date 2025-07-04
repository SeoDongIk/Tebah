package com.example.presentation.main.member

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.example.presentation.navigation.MemberMainNavHost
import com.example.presentation.theme.TebahTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemberMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = window
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        window.navigationBarColor = Color.White.toArgb()
        controller.isAppearanceLightNavigationBars = true

        setContent {
            TebahTheme {
                MemberMainNavHost()
            }
        }
    }
}