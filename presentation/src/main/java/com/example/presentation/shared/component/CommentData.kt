package com.example.presentation.shared.component

import androidx.compose.ui.graphics.painter.Painter

data class CommentData(
    val id: String,
    val authorName: String,
    val content: String,
    val timestamp: String,
    val profileImage: Painter? = null
)
