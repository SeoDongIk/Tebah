package com.example.presentation.auth.screen

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.UserRole
import com.example.presentation.R
import com.example.presentation.admin.AdminActivity
import com.example.presentation.auth.state.SignUpSideEffect
import com.example.presentation.auth.state.SignUpState
import com.example.presentation.auth.viewmodel.SignUpViewModel
import com.example.presentation.common.component.LargeButton
import com.example.presentation.common.theme.GradientNavy
import com.example.presentation.common.theme.Paddings
import com.example.presentation.common.theme.TebahTheme
import com.example.presentation.member.MemberActivity
import org.orbitmvi.orbit.compose.collectSideEffect

/* ---------------- Screen (side-effects + back block) ---------------- */

@Composable
fun <S : SignUpState> CompleteScreen(
    viewModel: SignUpViewModel<S>
) {
    val context = LocalContext.current
    val state = viewModel.container.stateFlow.collectAsStateWithLifecycle().value

    BackHandler(enabled = true) {}

    viewModel.collectSideEffect { effect ->
        when (effect) {
            is SignUpSideEffect.Toast ->
                Toast.makeText(context, context.getString(effect.messageRes), Toast.LENGTH_SHORT).show()

            is SignUpSideEffect.NavigateToMain -> {
                val target = if (effect.role == UserRole.ADMIN) AdminActivity::class.java
                else MemberActivity::class.java
                context.startActivity(
                    Intent(context, target).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                )
            }
            else -> Unit
        }
    }

    CompleteBody(
        onGoToMain = { viewModel.onSignInClick() },
        isLoading = state.isSigningIn       // ✅ 확인 버튼 로딩
    )
}

/* ---------------- View (Preview 가능한 순수 UI) ---------------- */

@Composable
private fun CompleteBody(
    onGoToMain: () -> Unit,
    isLoading: Boolean = false,
) {
    // 배경 브러시
    val bgBrush = remember {
        Brush.verticalGradient(
            colors = listOf(GradientNavy.copy(alpha = 0f), GradientNavy.copy(alpha = 1f)),
            startY = 0f,
            endY = 1000f
        )
    }

    // ---- 미세 조정 상수 ----
    val bgRightGap = 28.dp        // 배경 오른쪽 여백
    val bgDownOffset = 105.dp     // 배경 세로 오프셋(더 아래)

    // 🔧 블러 크롭 방지: (baseFraction × scale) ≤ 1.0 유지
    val spotBaseFraction = 0.74f
    val spotScale = 1.35f         // 0.74 × 1.35 ≈ 0.999 → 꽉 차 보이면서 잘리지 않음
    val spotDownOffset = 20.dp
    val spotAlpha = 0.65f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = bgBrush)
    ) {
        // 1) 배경 일러스트: 왼쪽에 붙이고, 오른쪽만 여백 + 더 아래로
        Image(
            painter = painterResource(id = R.drawable.ic_complete_background),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(end = bgRightGap)
                .fillMaxWidth()
                .offset(y = bgDownOffset),
            contentScale = ContentScale.FillWidth
        )

        // 2) 중앙 블러 스팟: 더 크게 보이되 화면 밖으로 안 나가게
        Image(
            painter = painterResource(id = R.drawable.ic_blurry_spot),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(spotBaseFraction)
                .graphicsLayer {
                    scaleX = spotScale
                    scaleY = spotScale
                }
                .alpha(spotAlpha)
                .offset(y = spotDownOffset),
            contentScale = ContentScale.Fit
        )

        // 3) 텍스트/버튼 레이어
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
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.complete_title_line2),
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )
            }

            // 가운데 공간(이미지는 루트 Box에 배치됨)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {}

            // 하단 버튼
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LargeButton(
                    onClick = onGoToMain,
                    text = stringResource(R.string.button_go_to_main),
                    isLoading = isLoading               // ✅ 로딩 바인딩
                )
            }
        }
    }
}

/* ---------------- Previews ---------------- */

@Preview(name = "Complete - Light", showBackground = true)
@Composable
private fun CompleteBodyPreview_Light() {
    TebahTheme {
        CompleteBody(
            onGoToMain = {},
            isLoading = false
        )
    }
}