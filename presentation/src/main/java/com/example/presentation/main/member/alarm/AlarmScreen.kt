package com.example.presentation.main.member.alarm

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation.component.AlarmCard
import com.example.presentation.component.TebahTopBar
import com.example.presentation.theme.Paddings
import com.example.presentation.theme.TebahTheme
import com.example.presentation.theme.TebahTypography
import com.example.presentation.theme.primary
import com.example.presentation.theme.third_03

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlarmScreen(
    pullProgress: Float
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        stickyHeader {
            TebahTopBar("알림")
        }

        item {

            Spacer(modifier = Modifier.size(Paddings.large))

            Text(
                text = "오늘 도착한 알림",
                style = TebahTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = Paddings.extra),
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(Paddings.small))
            AlarmCard("승인 완료", "[IT 선교소모임] 채널에서 [테바프리] 채널에 요청한 공지글 게시가 승인되었습니다.",
                "1시간 전") {
            }
            AlarmCard("승인 완료", "[IT 선교소모임] 채널에서 [테바프리] 채널에 요청한 공지글 게시가 승인되었습니다.",
                "1시간 전") {
            }

            Spacer(modifier = Modifier.size(Paddings.large))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "이전 알림",
                    style = TebahTypography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = Paddings.extra),
                    color = Color.Black
                )
                Text(
                    text = "알림 전체 삭제",
                    style = TebahTypography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = Paddings.extra),
                    color = third_03
                )
            }
            Spacer(modifier = Modifier.size(Paddings.small))
            AlarmCard("승인 완료", "[IT 선교소모임] 채널에서 [테바프리] 채널에 요청한 공지글 게시가 승인되었습니다.",
                "1시간 전") {
            }
            AlarmCard("승인 완료", "[IT 선교소모임] 채널에서 [테바프리] 채널에 요청한 공지글 게시가 승인되었습니다.",
                "1시간 전") {
            }
            AlarmCard("승인 완료", "[IT 선교소모임] 채널에서 [테바프리] 채널에 요청한 공지글 게시가 승인되었습니다.",
                "1시간 전") {
            }
            AlarmCard("승인 완료", "[IT 선교소모임] 채널에서 [테바프리] 채널에 요청한 공지글 게시가 승인되었습니다.",
                "1시간 전") {
            }

            Spacer(modifier = Modifier.size(Paddings.xlarge))
        }
    }


}

//@Preview(showBackground = true)
//@Composable
//fun AlarmScreenPreview() {
//    TebahTheme {
//        AlarmScreen()
//    }
//}