package com.example.presentation.common.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = primary,
    onPrimary = Color.White,
    primaryContainer = secondary,
    onPrimaryContainer = Color.White,
    surface = Color.Black,
    onSurface = Color.White,
    background = background,
    onBackground = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = primary,
    onPrimary = Color.Black,
    primaryContainer = secondary,
    onPrimaryContainer = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    background = background,
    onBackground = Color.Black
)

@Composable
fun TebahTheme(
    content: @Composable () -> Unit
) {
    val darkTheme: Boolean = isSystemInDarkTheme()
    val colorScheme = remember(darkTheme) {
        if(darkTheme) DarkColorScheme else LightColorScheme
    }
    val view = LocalView.current
    if(!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.White.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = TebahTypography,
        content = content
    )
}