package com.example.presentation.admin.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.common.component.MediumButton
import com.example.presentation.common.theme.Paddings
import com.example.presentation.common.theme.primary

@Composable
fun UnapprovedAdminScreen(
//    onNavigateToRole: () -> Unit,
//    onNavigateToMain: () -> Unit
) {
    var AdminName by remember { mutableStateOf("") }
    var AdminId by remember { mutableStateOf("") }
    var AdminPassword by remember { mutableStateOf("") }
    var AdminPasswordChecked by remember { mutableStateOf("") }

    val isNextEnabled = AdminName.isNotBlank() && AdminId.isNotBlank() && AdminPassword.isNotBlank() && AdminPasswordChecked.isNotBlank()
    val nextButtonColor = if (isNextEnabled) primary else Color.LightGray

    var selectedPosition by remember { mutableStateOf<String?>(null) }
    var customPosition by remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }
    val isCustomInput = selectedPosition == "직접입력"

    LaunchedEffect(selectedPosition) {
        if (isCustomInput) {
            focusRequester.requestFocus()
        } else {
            customPosition = ""
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
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
                text = "승인 대기중입니다",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(Paddings.xlarge))
            Text(
                text = "교회 채널 승인은\n2~3일 소요될 수 있습니다.",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Paddings.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier.size(200.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Paddings.extra),
            verticalArrangement = Arrangement.spacedBy(Paddings.xlarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MediumButton(
                text = "로그아웃",
                onClick = { },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}