package com.example.presentation.login.admin

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.component.LargeButton
import com.example.presentation.login.AdminSignUpViewModel
import com.example.presentation.theme.Paddings
import com.example.presentation.theme.TebahTheme
import com.example.presentation.theme.TebahTypography
import com.example.presentation.theme.primary
import com.example.presentation.theme.secondary
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun ChurchInfoScreen(
    viewModel: AdminSignUpViewModel,
    onNavigateToAdminInfo: () -> Unit
) {
    val state by viewModel.container.stateFlow.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }

    val isNextEnabled = state.churchName.isNotBlank() &&
            state.phoneNumber.isNotBlank() &&
            state.churchIntro.isNotBlank() &&
            state.region.isNotBlank()

    val nextButtonColor = if (isNextEnabled) primary else Color.LightGray

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = Paddings.layout_horizontal,
                vertical = Paddings.layout_vertical
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "교회 정보를",
                style = TebahTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
            Text(
                text = "입력해주세요",
                style = TebahTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Paddings.xlarge),
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = state.churchName,
                onValueChange = viewModel::onChurchNameChange,
                label = { Text("교회 이름") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )

            // 지역 선택
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showBottomSheet = true }
                    .border(1.dp, Color.Gray, RoundedCornerShape(Paddings.small))
                    .padding(Paddings.xlarge)
            ) {
                Text(
                    text = if (state.region.isNotBlank()) state.region else "지역 선택",
                    color = if (state.region.isNotBlank()) Color.Black else Color.Gray
                )
            }

            OutlinedTextField(
                value = state.phoneNumber,
                onValueChange = viewModel::onPhoneNumberChange,
                label = { Text("교회 전화번호") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )

            OutlinedTextField(
                value = state.churchIntro,
                onValueChange = viewModel::onChurchIntroChange,
                label = { Text("교회 소개") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LargeButton(
                onClick = {
                    if (isNextEnabled) {
                        onNavigateToAdminInfo()
                    }
                },
                text = "다음",
                backgroundColor = nextButtonColor
            )
        }
    }

    RegionSelectorModal(
        show = showBottomSheet,
        onDismissRequest = { showBottomSheet = false },
        selectedRegion = state.region,
        onRegionSelected = {
            viewModel.onRegionChange(it)
            showBottomSheet = false
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//fun ChurchInfoScreenPreview() {
//    TebahTheme {
//        ChurchInfoScreen()
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionSelectorModal(
    show: Boolean,
    onDismissRequest: () -> Unit,
    selectedRegion: String,
    onRegionSelected: (String) -> Unit
) {
    val regions = listOf("서울", "인천/경기", "대전/충남", "광주/전남", "대구/경북", "부산/경남")
    if (show) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true
            ),
            containerColor = Color.White,
            contentColor = Color.Black,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = Paddings.layout_vertical,
                        start = Paddings.layout_horizontal,
                        end = Paddings.layout_horizontal
                    )
            ) {
                // 헤더
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("지역 선택", style = TebahTypography.titleMedium.copy(fontWeight = FontWeight.Bold))
                    IconButton(onClick = onDismissRequest) {
                        Icon(Icons.Default.Close, contentDescription = "닫기", tint = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.height(Paddings.small))

                // 지역 선택 목록
                regions.forEach { region ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = { onRegionSelected(region) }
                            )
                            .padding(vertical = Paddings.xlarge),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(region,
                            style = TebahTypography.titleMedium,
                            color = Color.Black)
                        Spacer(modifier = Modifier.weight(1f))
                        if (region == selectedRegion) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "선택됨",
                                tint = secondary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(Paddings.xlarge))

                LargeButton(
                    onClick = onDismissRequest,
                    text = "확인",
                    backgroundColor = primary
                )
            }
        }
    }
}