package com.example.presentation.admin.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.common.component.MediumButton
import com.example.presentation.common.component.TebahTopBar
import com.example.presentation.common.theme.Paddings

@Composable
fun UnapprovedAdminScreen(
    onLogoutClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TebahTopBar()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = Paddings.layout_horizontal),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "승인 대기중입니다",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = Paddings.medium)
            )
            
            Text(
                text = "교회 채널 생성 승인은\n2-3일 소요될 수 있습니다.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = Paddings.xlarge)
            )

            // Lottie animation placeholder
            Surface(
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = Paddings.xlarge),
                shape = MaterialTheme.shapes.extraLarge,
                color = Color.LightGray
            ) {
                Text(
                    text = "LOTTIE",
                    modifier = Modifier.wrapContentSize(Alignment.Center),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.DarkGray
                )
            }

            MediumButton(
                text = "로그아웃",
                onClick = onLogoutClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}