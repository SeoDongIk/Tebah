package com.example.presentation.auth.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.R
import com.example.presentation.auth.state.SignUpSideEffect
import com.example.presentation.auth.viewmodel.MemberSignUpViewModel
import com.example.presentation.common.component.LargeButton
import com.example.presentation.common.component.MediumDialog
import com.example.presentation.common.component.TebahTopBar
import com.example.presentation.common.theme.Paddings
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ChurchSelectScreen(
    viewModel: MemberSignUpViewModel,
    onNavigateToComplete: () -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collectLatest { sideEffect ->
            when (sideEffect) {
                is SignUpSideEffect.Toast -> { /* 필요시 Toast/Snackbar */ }
                SignUpSideEffect.NavigateToCompleteScreen -> onNavigateToComplete()
                else -> Unit
            }
        }
    }

    // 파생값 & 안정 람다(리컴포지션 노이즈 감소)
    val regions = remember(state.churchesByRegion) { state.churchesByRegion.keys.toList() }
    val churchList = remember(state.selectedRegion, state.churchesByRegion) {
        state.churchesByRegion[state.selectedRegion].orEmpty()
    }

    Scaffold(
        topBar = { TebahTopBar(onBackClick = onBackClick) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 타이틀
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

            // 선택 배너 (부드러운 등장/퇴장)
            SelectedChurchBanner(
                churchName = state.selectedChurchId,
                churchRegion = stringResource(id = R.string.selected_church_region_default)
            )

            // 섹션 구분 라인
            Spacer(
                modifier = Modifier
                    .height(Paddings.medium)
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5))
            )

            // 지역 칩 + 교회 리스트
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
                RegionChips(
                    regions = regions,
                    selectedRegion = state.selectedRegion,
                    onRegionSelected = viewModel::onRegionSelected
                )

                ChurchesList(
                    churches = churchList,
                    selectedChurch = state.selectedChurchId,
                    onChurchSelected = viewModel::onChurchSelected
                )
            }

            // 다음 버튼
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
                    onClick = { viewModel.onSignUpClick() },
                    text = stringResource(id = R.string.next_button_text),
                    enabled = state.isChurchSelectValid,
                    isLoading = state.isSigningUp
                )
            }
        }
    }

    // 다이얼로그
    state.dialog?.let { d ->
        MediumDialog(
            showDialog = true,
            title = stringResource(d.titleRes),
            content = stringResource(d.messageRes),
            buttonContent = stringResource(d.confirmTextRes),
            onConfirm = { viewModel.dismissDialog() },
            onDismissRequest = { viewModel.dismissDialog() }
        )
    }
}

/* ---------------- Sub-Composables ---------------- */

@Composable
private fun RegionChips(
    regions: List<String>,
    selectedRegion: String,
    onRegionSelected: (String) -> Unit
) {
    FlowRow(
        mainAxisSpacing = Paddings.medium,
        crossAxisSpacing = Paddings.medium
    ) {
        regions.forEach { region ->
            val isSelected = region == selectedRegion
            Button(
                onClick = { onRegionSelected(region) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
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
}

@Composable
private fun ChurchesList(
    churches: List<String>,
    selectedChurch: String?,
    onChurchSelected: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 300.dp)
    ) {
        items(
            items = churches,
            key = { it } // 교회명 기준 안정 키
        ) { church ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Paddings.small)
                    .clickable { onChurchSelected(church) },
                colors = CardDefaults.cardColors(
                    containerColor = if (selectedChurch == church)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                    else Color.White
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

/* --------- Selected Banner: smoother in/out ---------- */

@Composable
private fun SelectedChurchBanner(
    churchName: String?,
    churchRegion: String
) {
    // 레이아웃 점프를 줄이기 위해 배너 자체에 expand/shrink + fade 조합
    AnimatedVisibility(
        visible = !churchName.isNullOrBlank(),
        enter = fadeIn(
            animationSpec = tween(durationMillis = 220, easing = FastOutSlowInEasing)
        ) + expandVertically(
            animationSpec = tween(durationMillis = 220, easing = FastOutSlowInEasing),
            expandFrom = Alignment.Top
        ),
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = 220, easing = FastOutSlowInEasing),
            shrinkTowards = Alignment.Top
        ) + fadeOut(
            animationSpec = tween(durationMillis = 220, easing = FastOutSlowInEasing)
        )
    ) {
        Column {
            Spacer(modifier = Modifier.height(Paddings.medium))
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
                        text = churchName ?: "",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = churchRegion,
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                    )
                }
            }
            Spacer(modifier = Modifier.height(Paddings.large))
        }
    }
}