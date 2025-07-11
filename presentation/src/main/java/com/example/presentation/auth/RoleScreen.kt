package com.example.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.presentation.R
import com.example.presentation.component.LargeButton
import com.example.presentation.theme.Paddings
import com.example.presentation.theme.TebahTypography

@Composable
fun RoleScreen(
    onNavigateToMemberInfo: () -> Unit,
    onNavigateToChurchInfo: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
                style = TebahTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.role_title_line2),
                style = TebahTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
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
                text = stringResource(R.string.role_button_church_channel),
                onClick = onNavigateToChurchInfo
            )
            LargeButton(
                text = stringResource(R.string.role_button_personal_signup),
                onClick = onNavigateToMemberInfo
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