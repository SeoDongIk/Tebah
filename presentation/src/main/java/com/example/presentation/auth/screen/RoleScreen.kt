package com.example.presentation.auth.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.presentation.R
import com.example.presentation.common.component.LargeButton
import com.example.presentation.common.component.LargeOutlinedButton
import com.example.presentation.common.component.TebahTopBar
import com.example.presentation.common.theme.Paddings
import com.example.presentation.common.theme.TebahTheme

@Composable
fun RoleScreen(
    onNavigateToMemberInfo: () -> Unit,
    onNavigateToChurchInfo: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = { TebahTopBar(onBackClick) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Paddings.layout_horizontal, vertical = Paddings.layout_vertical)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = Paddings.layout_vertical)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.role_title_line1),
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.role_title_line2),
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Paddings.xlarge, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LargeButton(
                    text = stringResource(R.string.role_button_personal_signup),
                    onClick = onNavigateToMemberInfo
                )
                LargeOutlinedButton(
                    text = stringResource(R.string.role_button_church_channel),
                    onClick = onNavigateToChurchInfo
                )
            }

            // 빈 공간 (weight 1f) 필요하면 사용, 현재는 빈 Column 유지
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 필요 시 추가 UI 넣기
            }
        }
    }

}

@Preview(
    name = "RoleScreen - Light",
    showBackground = true
)
@Composable
fun RoleScreenPreviewLight() {
    TebahTheme {
        RoleScreen(
            onNavigateToMemberInfo = {},
            onNavigateToChurchInfo = {},
            onBackClick = {}
        )
    }
}

@Preview(
    name = "RoleScreen - Dark",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun RoleScreenPreviewDark() {
    TebahTheme {
        RoleScreen(
            onNavigateToMemberInfo = {},
            onNavigateToChurchInfo = {},
            onBackClick = {}
        )
    }
}