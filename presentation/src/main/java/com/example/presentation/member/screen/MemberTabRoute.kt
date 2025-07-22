package com.example.presentation.member.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

enum class MemberTabRoute(val route: String, val icon: ImageVector, val contentDescription: String) {
    HOME("home", Icons.Default.Home, "홈"),
    SEARCH("search", Icons.Default.Search, "검색"),
    WRITE("write", Icons.Default.Add, "글쓰기"),
    NOTIFICATION("notification", Icons.Default.ThumbUp, "알림"),
    USER("user", Icons.Default.Person, "유저");
}