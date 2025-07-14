package com.example.presentation.auth.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation.common.component.LargeButton
import com.example.presentation.auth.viewmodel.MemberSignUpViewModel
import com.example.presentation.auth.state.SignUpSideEffect
import com.example.presentation.common.theme.Paddings
import com.example.presentation.common.theme.primary
import com.example.presentation.common.theme.third_01
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ChurchSelectScreen(
    viewModel: MemberSignUpViewModel,
    onNavigateToComplete: () -> Unit
) {
    val state by viewModel.container.stateFlow.collectAsState()
    val sideEffectFlow = viewModel.container.sideEffectFlow

    val churchesByRegion = state.churchesByRegion
    val selectedRegion = state.selectedRegion
    val selectedChurch = state.selectedChurchId
    val nextButtonColor = if (selectedChurch != null) primary else Color.LightGray

    LaunchedEffect(Unit) {
        sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is SignUpSideEffect.Toast -> {
                    // Toast 처리 (예: Android Toast or Compose Snackbar)
                }
                SignUpSideEffect.NavigateToCompleteScreen -> {
                    onNavigateToComplete()
                }
                else -> { /* 다른 이벤트 처리 */ }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 타이틀 영역
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    top = Paddings.layout_vertical,
                    start = Paddings.layout_horizontal,
                    end = Paddings.layout_horizontal
                )
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.church_select_title_1),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            )
            Text(
                text = stringResource(id = R.string.church_select_title_2),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        // 선택된 교회 배너 표시
        selectedChurch?.let {
            Spacer(modifier = Modifier.height(Paddings.medium))
            SelectedChurchBanner(
                churchName = it,
                churchRegion = stringResource(id = R.string.selected_church_region_default) // 기본값
            )
            Spacer(modifier = Modifier.height(Paddings.large))
        }

        Spacer(
            modifier = Modifier
                .height(Paddings.medium)
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
        )

        // 지역 선택 버튼 및 교회 리스트
        Column(
            modifier = Modifier
                .padding(
                    top = Paddings.layout_vertical,
                    start = Paddings.layout_horizontal,
                    end = Paddings.layout_horizontal
                )
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Paddings.xlarge),
            horizontalAlignment = Alignment.Start
        ) {
            FlowRow(
                mainAxisSpacing = Paddings.medium,
                crossAxisSpacing = Paddings.medium
            ) {
                churchesByRegion.keys.forEach { region ->
                    val isSelected = region == selectedRegion
                    Button(
                        onClick = { viewModel.onRegionSelected(region) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) primary else Color.LightGray
                        ),
                        shape = RoundedCornerShape(Paddings.xlarge),
                        contentPadding = PaddingValues(
                            horizontal = Paddings.xlarge,
                            vertical = Paddings.medium
                        )
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
                            .clickable { viewModel.onChurchSelected(church) },
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedChurch == church) third_01.copy(alpha = 0.4f) else Color.White
                        )
                    ) {
                        Text(
                            text = church,
                            modifier = Modifier.padding(Paddings.xlarge),
                            fontWeight = if (selectedChurch == church) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedChurch == church) Color.White else Color.Black
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .height(Paddings.xsmall)
                            .fillMaxWidth()
                            .background(Color(0xFFF5F5F5))
                    )
                }
            }
        }

        // 다음 버튼 영역
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    bottom = Paddings.layout_vertical,
                    start = Paddings.layout_horizontal,
                    end = Paddings.layout_horizontal
                )
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LargeButton(
                onClick = {
                    if (selectedChurch != null) { viewModel.onSignUpClick() }
                },
                text = stringResource(id = R.string.next_button_text),
                backgroundColor = nextButtonColor
            )
        }
    }
}

@Composable
fun SelectedChurchBanner(
    churchName: String,
    churchRegion: String = stringResource(id = R.string.selected_church_region_default)
) {
    AnimatedVisibility(
        visible = churchName.isNotBlank(),
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
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF5F5F5))
            )

            Spacer(modifier = Modifier.width(Paddings.xlarge))

            Column {
                Text(
                    text = churchName,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = churchRegion,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                )
            }
        }
    }
}