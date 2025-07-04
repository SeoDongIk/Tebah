package com.example.presentation.main.member.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ContentSection2() {
    var itemCount by remember { mutableStateOf(2) }

    Column(modifier = Modifier.padding(horizontal = 0.dp)) {
        Text("최근 본 게시물", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        repeat(itemCount) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(vertical = 4.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text("게시물 ${it + 1}")
            }
        }

        if (itemCount < 20) {
            TextButton(
                onClick = {
                    itemCount = (itemCount + 5).coerceAtMost(20)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("더보기")
            }
        }
    }
}