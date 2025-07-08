package com.example.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.theme.Paddings
import com.example.presentation.theme.TebahTheme
import com.example.presentation.theme.third_03

@Composable
fun AlarmCard(
    topLeftText: String,
    centerText: String,
    bottomLeftText: String,
    onCloseClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Paddings.xlarge, vertical = Paddings.medium),
        shape = RoundedCornerShape(Paddings.xlarge),
        elevation = CardDefaults.cardElevation(defaultElevation = Paddings.small),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Paddings.xlarge),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // 상단
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = topLeftText,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                IconButton(
                    onClick = onCloseClick,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = third_03
                    )
                }
            }

            Spacer(modifier = Modifier.size(Paddings.small))

            // 가운데
            Text(
                text = centerText,
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Paddings.medium)  // 위아래 여백만 살짝
            )

            Spacer(modifier = Modifier.size(Paddings.small))

            // 하단
            Text(
                text = bottomLeftText,
                fontSize = 14.sp,
                color = third_03,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlarmCardPreview() {
    TebahTheme {
        AlarmCard("승인 완료", "[IT 선교소모임] 채널에서 [테바프리] 채널에 요청한 공지글 게시가 승인되었습니다.",
            "1시간 전") {

        }
    }
}