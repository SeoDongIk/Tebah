package com.example.presentation.main.member.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    isSearching: Boolean,
    onSearchClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val animatedWeight by animateFloatAsState(
            targetValue = if (isSearching) 0.8f else 1f,
            animationSpec = tween(300),
            label = "searchBarWeight"
        )

        Box(
            modifier = Modifier
                .weight(animatedWeight)
                .height(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.LightGray)
                .clickable { onSearchClick() },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "검색어를 입력하세요",
                modifier = Modifier.padding(start = 16.dp),
                color = Color.DarkGray
            )
        }

        AnimatedVisibility(
            visible = isSearching,
            enter = fadeIn(tween(300)),
            exit = fadeOut(tween(300))
        ) {
            TextButton(onClick = onCancelClick) {
                Text("취소")
            }
        }
    }
}