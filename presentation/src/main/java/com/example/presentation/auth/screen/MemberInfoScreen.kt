package com.example.presentation.auth.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.R
import com.example.presentation.auth.state.SignUpSideEffect
import com.example.presentation.auth.viewmodel.MemberSignUpViewModel
import com.example.presentation.common.component.LargeButton
import com.example.presentation.common.component.TebahTextField
import com.example.presentation.common.component.TebahTopBar
import com.example.presentation.common.theme.Paddings
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MemberInfoScreen(
    viewModel: MemberSignUpViewModel,
    onNavigateToChurchSelect: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    // 사이드이펙트: 토스트 & 네비게이션
    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collectLatest { effect ->
            when (effect) {
                is SignUpSideEffect.Toast ->
                    Toast.makeText(context, context.getString(effect.messageRes), Toast.LENGTH_SHORT).show()
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = { TebahTopBar(onBackClick = onBackClick) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(
                    horizontal = Paddings.layout_horizontal,
                    vertical = Paddings.layout_vertical
                )
        ) {
            // 타이틀(좌측 정렬)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.member_info_title_line1),
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = stringResource(id = R.string.member_info_title_line2),
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            // 입력 폼
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Paddings.xlarge),
                horizontalAlignment = Alignment.Start
            ) {
                // 이름
                TebahTextField(
                    value = state.name,
                    onValueChange = viewModel::onNameChange,
                    label = stringResource(R.string.label_name),
                    isError = state.nameError != null,
                    errorMessage = state.nameError?.let { stringResource(it) }
                )

                // 아이디(이메일)
                TebahTextField(
                    value = state.id,
                    onValueChange = viewModel::onIdChange,
                    label = stringResource(R.string.label_id),
                    isError = state.idError != null,
                    errorMessage = state.idError?.let { stringResource(it) }
                )

                // 비밀번호
                TebahTextField(
                    value = state.password,
                    onValueChange = viewModel::onPasswordChange,
                    label = stringResource(R.string.label_password),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = state.passwordError != null,
                    errorMessage = state.passwordError?.let { stringResource(it) }
                )

                // 비밀번호 확인
                TebahTextField(
                    value = state.repeatPassword,
                    onValueChange = viewModel::onRepeatPasswordChange,
                    label = stringResource(R.string.label_password_confirm),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = state.repeatPasswordError != null,
                    errorMessage = state.repeatPasswordError?.let { stringResource(it) }
                )
            }

            // 하단 버튼
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LargeButton(
                    onClick = onNavigateToChurchSelect,
                    text = stringResource(id = R.string.button_next),
                    enabled = state.isMemberInfoValid
                )
            }
        }
    }
}