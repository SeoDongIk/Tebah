package com.example.presentation.login.member

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.component.LargeButton
import com.example.presentation.login.MemberSignUpViewModel
import com.example.presentation.theme.Paddings
import com.example.presentation.theme.TebahTheme
import com.example.presentation.theme.TebahTypography
import com.example.presentation.theme.primary
import com.example.presentation.theme.secondary
import com.example.presentation.theme.third_01
import com.example.presentation.theme.third_03
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ChurchSelectScreen(
    viewModel: MemberSignUpViewModel,
    onNavigateToComplete: () -> Unit
) {
    var selectedRegion by remember { mutableStateOf("전체") }
    val churchesByRegion = mapOf(
        "전체" to listOf("은혜교회", "사랑교회", "하나교회", "기쁨교회","은혜교회2", "사랑교회2", "하나교회2", "기쁨교회2"),
        "서울" to listOf("서울제일교회", "강남은혜교회"),
        "인천/경기" to listOf("수원교회", "인천믿음교회"),
        "대전/충남" to listOf("대전중앙교회"),
        "광주/전남" to listOf("광주은혜교회"),
        "대구/경북" to listOf("대구제일교회"),
        "부산/경남" to listOf("부산영광교회")
    )

    // ViewModel에서 상태 관찰
    val state by viewModel.container.stateFlow.collectAsState()
    val selectedChurch = state.selectedChurchId

    val nextButtonColor = if (selectedChurch != null) primary else Color.LightGray

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = Paddings.layout_vertical, start = Paddings.layout_horizontal, end = Paddings.layout_horizontal)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "교회를",
                style = TebahTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
            Text(
                text = "선택해주세요",
                style = TebahTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
        }

        selectedChurch?.let {
            Spacer(modifier = Modifier.height(Paddings.medium))
            SelectedChurchBanner(
                churchName = it,
                churchRegion = "서울 강남구" // 고정된 값이지만 추후 동적으로 바꿔도 됩니다.
            )
            Spacer(modifier = Modifier.height(Paddings.large))
        }

        Spacer(modifier = Modifier.height(Paddings.medium)
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5)))

        Column(
            modifier = Modifier
                .padding(top = Paddings.layout_vertical, start = Paddings.layout_horizontal,
                    end = Paddings.layout_horizontal)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Paddings.xlarge),
            horizontalAlignment = Alignment.Start
        ) {
            FlowRow(
                mainAxisSpacing = Paddings.medium,
                crossAxisSpacing = Paddings.medium
            ) {
                val regions = churchesByRegion.keys.toList()
                regions.forEach { region ->
                    val isSelected = region == selectedRegion
                    Button(
                        onClick = { selectedRegion = region },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) primary else Color.LightGray
                        ),
                        shape = RoundedCornerShape(Paddings.xlarge),
                        contentPadding = PaddingValues(horizontal = Paddings.xlarge, vertical = Paddings.medium)
                    ) {
                        Text(
                            text = region,
                            fontSize = 14.sp,
                            color = if (isSelected) Color.White else Color.Black
                        )
                    }
                }
            }

            val churchList = churchesByRegion[selectedRegion] ?: emptyList()
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
            ) {
                items(churchList) { church ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Paddings.small)
                            .clickable {
                                viewModel.onChurchSelect(church) // ViewModel에 선택된 교회 전달
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedChurch == church) third_01.copy(0.4f) else Color.White
                        )
                    ) {
                        Text(
                            text = church,
                            modifier = Modifier.padding(Paddings.xlarge),
                            fontWeight = if (selectedChurch == church) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedChurch == church) Color.White else Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.height(Paddings.xsmall)
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5)))
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = Paddings.layout_vertical, start = Paddings.layout_horizontal,
                    end = Paddings.layout_horizontal)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LargeButton(
                onClick = {
                    if (selectedChurch != null) {
                        onNavigateToComplete()
                    }
                },
                text = "다음",
                backgroundColor = nextButtonColor
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ChurchSelectScreenPreview() {
//    TebahTheme {
//        ChurchSelectScreen()
//    }
//}

@Composable
fun SelectedChurchBanner(
    churchName: String,
    churchRegion: String = "지역 정보 없음"
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)) +
                slideInVertically(
                    initialOffsetY = { fullHeight -> -fullHeight / 2 },
                    animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(animationSpec = tween(300)) +
                slideOutVertically(
                    targetOffsetY = { fullHeight -> -fullHeight / 2 },
                    animationSpec = tween(300)
                )

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = Paddings.layout_horizontal)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(Paddings.large)
                )
                .padding(Paddings.large),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 왼쪽 원형 이미지 영역
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF5F5F5)) // 회색
            )

            Spacer(modifier = Modifier.width(Paddings.xlarge))

            // 오른쪽 텍스트 2줄
            Column {
                Text(
                    text = churchName,
                    style = TebahTypography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = churchRegion,
                    style = TebahTypography.bodyMedium.copy(color = Color.Gray)
                )
            }
        }
    }
}