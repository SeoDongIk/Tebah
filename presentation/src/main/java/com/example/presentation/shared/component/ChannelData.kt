package com.example.presentation.shared.component

import androidx.compose.ui.graphics.painter.Painter

data class ChannelData(
    val id: String,
    val name: String,
    val description: String,
    val subscriberCount: Int,
    val thumbnail: Painter? = null
)