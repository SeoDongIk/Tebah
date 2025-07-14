package com.example.presentation.member.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class MainRoute(
    val contentDescription: String,
    val icon: ImageVector
){
    HOME(contentDescription = "홈", icon = Icons.Filled.Home),
    SEARCH(contentDescription = "검색", icon = Icons.Filled.Search),
    WRITE(contentDescription = "글쓰기", icon = Icons.Filled.Add),
    LIKE(contentDescription = "좋아요", icon = Icons.Filled.FavoriteBorder),
    USER(contentDescription = "유저", icon = Icons.Filled.AccountCircle)
}