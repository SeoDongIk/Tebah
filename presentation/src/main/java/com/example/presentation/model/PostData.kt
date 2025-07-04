package com.example.presentation.model

import androidx.compose.ui.graphics.painter.Painter

data class PostData(
    val isNotice: Boolean,
    val hasImages: Boolean,
    val profileImage: Painter,
    val userId: String,
    val postTime: String,
    val previewText: String,
    val imageList: List<Painter> = emptyList()
)