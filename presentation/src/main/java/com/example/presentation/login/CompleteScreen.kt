package com.example.presentation.login

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.presentation.R
import com.example.presentation.component.LargeButton
import com.example.presentation.main.admin.AdminMainActivity
import com.example.presentation.main.member.MemberMainActivity
import com.example.presentation.model.SignUpSideEffect
import com.example.presentation.model.SignUpState
import com.example.presentation.model.SignUpViewModel
import com.example.presentation.theme.Paddings
import com.example.presentation.theme.TebahTypography
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun <S : SignUpState> CompleteScreen(
    viewModel: SignUpViewModel<S>
) {
    val context = LocalContext.current

    // 뒤로가기 막기 (필요 시 다이얼로그로 변경 가능)
    BackHandler(enabled = true) {}

    // 사이드이펙트 처리
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SignUpSideEffect.Toast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }

            is SignUpSideEffect.NavigateToMainActivity -> {
                val intent = Intent(
                    context,
                    if (sideEffect.isAdmin) AdminMainActivity::class.java
                    else MemberMainActivity::class.java
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            }

            else -> {
                // 이 스크린에서는 무시되거나 예외처리 필요
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Paddings.layout_horizontal, vertical = Paddings.layout_vertical)
    ) {
        // 상단 텍스트
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = stringResource(R.string.complete_title_line1),
                style = TebahTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.complete_title_line2),
                style = TebahTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
        }

        // 이미지 or 공간 자리
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Spacer(modifier = Modifier.size(200.dp)) // 이미지 자리
        }

        // 버튼
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LargeButton(
                onClick = { viewModel.onSignInClick() },
                text = stringResource(R.string.button_go_to_main)
            )
        }
    }
}