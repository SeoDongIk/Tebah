package com.example.presentation.common.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.presentation.R

private val pretendardFontFamily = FontFamily(Font(R.font.pretendard))
private val defaultTypography = Typography()
private fun Typography.withFontFamily(fontFamily: FontFamily): Typography = Typography(
    displayLarge  = displayLarge.copy(fontFamily = fontFamily),
    displayMedium = displayMedium.copy(fontFamily = fontFamily),
    displaySmall  = displaySmall.copy(fontFamily = fontFamily),
    headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
    headlineMedium= headlineMedium.copy(fontFamily = fontFamily),
    headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
    titleLarge    = titleLarge.copy(fontFamily = fontFamily),
    titleMedium   = titleMedium.copy(fontFamily = fontFamily),
    titleSmall    = titleSmall.copy(fontFamily = fontFamily),
    bodyLarge     = bodyLarge.copy(fontFamily = fontFamily),
    bodyMedium    = bodyMedium.copy(fontFamily = fontFamily),
    bodySmall     = bodySmall.copy(fontFamily = fontFamily),
    labelLarge    = labelLarge.copy(fontFamily = fontFamily),
    labelMedium   = labelMedium.copy(fontFamily = fontFamily),
    labelSmall    = labelSmall.copy(fontFamily = fontFamily),
)

val TebahTypography: Typography = defaultTypography.withFontFamily(pretendardFontFamily)