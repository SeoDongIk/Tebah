package com.example.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.theme.Paddings
import com.example.presentation.theme.primary
import com.example.presentation.theme.secondary
import com.example.presentation.theme.third_01
import com.example.presentation.theme.third_02
import com.example.presentation.theme.third_03

@Composable
fun MainBottomNavigationBar(
    currentRoute: MainRoute,
    onItemSelected: (MainRoute) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(Paddings.medium)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = Paddings.small,
                    color = third_01.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(Paddings.xlarge)
                )
                .clip(shape = RoundedCornerShape(Paddings.xlarge))
                .background(Color.White)
                .padding(vertical = Paddings.medium, horizontal = Paddings.xlarge),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MainRoute.values().forEach { screen ->
                val selected = currentRoute == screen
                IconButton(
                    onClick = { onItemSelected(screen) },
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(
                        modifier = if (selected) {
                            Modifier
                                .size(36.dp)
                                .background(secondary.copy(alpha = 0.1f), shape = CircleShape)
                                .padding(5.dp)
                        } else Modifier
                    ) {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = screen.contentDescription,
                            modifier = Modifier.size(26.dp),
                            tint = if (selected) primary else third_01.copy(alpha = 1f)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainBottomNavigationBarPreview() {
    val currentRoute = remember { mutableStateOf(MainRoute.HOME) }

    MainBottomNavigationBar(
        currentRoute = currentRoute.value,
        onItemSelected = { selected ->
            currentRoute.value = selected
        }
    )
}