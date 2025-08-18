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

val LightColorScheme = lightColorScheme(
    primary = Navy,
    onPrimary = White,
    primaryContainer = LightNavy,
    onPrimaryContainer = White,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Black,
    surfaceVariant = White,
    onSurfaceVariant = Gray,
    outline = Gray,
    outlineVariant = Navy,
    error = Orange,
    onError = White,
    errorContainer = Orange,
    onErrorContainer = White
)

val DarkColorScheme = darkColorScheme(
    primary = Navy,
    onPrimary = White,
    primaryContainer = LightNavy,
    onPrimaryContainer = White,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Black,
    surfaceVariant = White,
    onSurfaceVariant = Gray,
    outline = Gray,
    outlineVariant = Navy,
    error = Orange,
    onError = White,
    errorContainer = Orange,
    onErrorContainer = White
)

@Composable
fun TebahTheme(
    content: @Composable () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()
    val colorScheme = remember(darkTheme) { if(darkTheme) DarkColorScheme else LightColorScheme }
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