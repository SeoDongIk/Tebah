package com.example.presentation.admin

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.example.presentation.common.theme.TebahTheme
import com.example.presentation.admin.screen.AdminMainScreen
import com.example.presentation.admin.screen.UnapprovedAdminScreen
import com.example.presentation.common.theme.Navy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = window
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        window.navigationBarColor = Navy.toArgb()
        controller.isAppearanceLightNavigationBars = true

        setContent {
            TebahTheme {
                val isApproved = true

                if (isApproved) {
                    AdminMainScreen()
                } else {
                    UnapprovedAdminScreen()
                }
            }
        }
    }
}