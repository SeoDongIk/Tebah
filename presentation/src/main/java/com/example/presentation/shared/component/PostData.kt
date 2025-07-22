package com.example.presentation.shared.component

import androidx.compose.ui.graphics.painter.Painter

data class PostData(
    val id: String,
    val isOfficial: Boolean,
    val hasImages: Boolean,
    val profileImage: Painter,
    val userId: String,
    val postTime: String,
    val previewText: String,
    val imageList: List<Painter> = emptyList(),
    val likeCount: Int = 0,
    val saveCount: Int = 0,
    val checkCount: Int = 0,
    val isNotice: Boolean = false
)