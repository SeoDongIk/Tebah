package com.example.presentation.auth.screen

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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.domain.model.Region
import com.example.presentation.R
import com.example.presentation.common.component.LargeButton
import com.example.presentation.auth.viewmodel.AdminSignUpViewModel
import com.example.presentation.common.theme.Paddings

@Composable
fun ChurchInfoScreen(
    viewModel: AdminSignUpViewModel,
    onNavigateToAdminInfo: () -> Unit
) {
    val state by viewModel.container.stateFlow.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val isNextEnabled = state.isChurchInfoValid
    val nextButtonColor = if (isNextEnabled)  MaterialTheme.colorScheme.primary else Color.LightGray

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = Paddings.layout_horizontal,
                vertical = Paddings.layout_vertical
            )
    ) {
        // 타이틀 영역
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.church_info_title_1),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = R.string.church_info_title_2),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
        }

        // 입력폼 영역
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Paddings.xlarge),
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = state.churchName,
                onValueChange = viewModel::onChurchNameChange,
                label = { Text(stringResource(id = R.string.church_name_label)) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showBottomSheet = true }
                    .border(1.dp, Color.Gray, RoundedCornerShape(Paddings.small))
                    .padding(Paddings.xlarge)
            ) {
                Text(
                    text = state.region?.displayName ?: stringResource(id = R.string.region_select_hint),
                    color = if (state.region != null) Color.Black else Color.Gray
                )
            }

            OutlinedTextField(
                value = state.phoneNumber,
                onValueChange = viewModel::onPhoneNumberChange,
                label = { Text(stringResource(id = R.string.church_phone_label)) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )

            OutlinedTextField(
                value = state.churchIntro,
                onValueChange = viewModel::onChurchIntroChange,
                label = { Text(stringResource(id = R.string.church_intro_label)) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )
        }

        // 다음 버튼 영역
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LargeButton(
                onClick = { if (isNextEnabled) onNavigateToAdminInfo() },
                text = stringResource(id = R.string.next_button_text)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionSelectorModal(
    show: Boolean,
    onDismissRequest: () -> Unit,
    selectedRegion: Region?,
    onRegionSelected: (Region) -> Unit
) {
    if (!show) return

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(id = R.string.region_selector_title),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                IconButton(onClick = onDismissRequest) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.close_button_desc),
                        tint = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(Paddings.small))

            Region.values().forEach { region ->
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
                    Text(region.displayName, style = MaterialTheme.typography.titleMedium, color = Color.Black)
                    Spacer(modifier = Modifier.weight(1f))
                    if (region == selectedRegion) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = stringResource(id = R.string.selected_text),
                            tint =  MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Paddings.xlarge))

            LargeButton(
                onClick = onDismissRequest,
                text = stringResource(id = R.string.confirm_button_text),
            )
        }
    }
}