package com.example.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.presentation.component.LargeButton
import com.example.presentation.theme.Paddings
import com.example.presentation.theme.TebahTheme
import com.example.presentation.theme.TebahTypography

@Composable
fun RoleScreen(
    onNavigateToMemberInfo: () -> Unit,
    onNavigateToChurchInfo: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Paddings.layout_horizontal,
                vertical = Paddings.layout_vertical)
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
                text = "역할을",
                style = TebahTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
            Text(
                text = "골라주세요",
                style = TebahTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Paddings.xlarge, Alignment.CenterVertically)
            ) {

            LargeButton(
                text = "교회 채널 만들기",
                onClick = onNavigateToChurchInfo
            )
            LargeButton(
                text = "개인 가입",
                onClick = onNavigateToMemberInfo
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun RoleScreenPreview() {
//    TebahTheme {
//        RoleScreen()
//    }
//}