package com.example.presentation.write

import androidx.compose.ui.graphics.painter.Painter

data class PostData2(
    val isNotice: Boolean,
    val hasImages: Boolean,
    val profileImage: Painter,
    val userId: String,
    val postTime: String,
    val previewText: String,
    val imageList: List<Painter> = emptyList()
)