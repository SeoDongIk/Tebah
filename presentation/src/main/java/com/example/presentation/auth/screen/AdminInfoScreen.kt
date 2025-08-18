package com.example.presentation.auth.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.example.domain.model.AdminPosition
import com.example.presentation.R
import com.example.presentation.common.component.LargeButton
import com.example.presentation.auth.viewmodel.AdminSignUpViewModel
import com.example.presentation.auth.state.SignUpSideEffect
import com.example.presentation.common.theme.Paddings

@Composable
fun AdminInfoScreen(
    viewModel: AdminSignUpViewModel,
    onNavigateToComplete: () -> Unit
) {
    val state by viewModel.container.stateFlow.collectAsState()
    val sideEffectFlow = viewModel.container.sideEffectFlow
    val focusRequester = remember { FocusRequester() }
    val isNextEnabled = state.isUserInfoValid
    val nextButtonColor = if (isNextEnabled)  MaterialTheme.colorScheme.primary else Color.LightGray

    LaunchedEffect(Unit) {
        sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is SignUpSideEffect.Toast -> {
                    // Toast 처리 (예: Android Toast or Compose Snackbar)
                }
                SignUpSideEffect.NavigateToCompleteScreen -> {
                    onNavigateToComplete()
                }
                else -> { /* 다른 이벤트 처리 */ }
            }
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
        // 타이틀 부분
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.admin_info_title_1),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = R.string.admin_info_title_2),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
        }

        // 관리자 정보 입력 폼
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Paddings.medium),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.size(Paddings.medium))
            Text(
                text = stringResource(id = R.string.admin_info_section_title),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = state.adminName,
                onValueChange = viewModel::onAdminNameChange,
                label = { Text(stringResource(id = R.string.admin_name_label)) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )
            OutlinedTextField(
                value = state.id,
                onValueChange = viewModel::onIdChange,
                label = { Text(stringResource(id = R.string.admin_id_label)) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text(stringResource(id = R.string.admin_password_label)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )
            OutlinedTextField(
                value = state.repeatPassword,
                onValueChange = viewModel::onRepeatPasswordChange,
                label = { Text(stringResource(id = R.string.admin_password_confirm_label)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )
            Spacer(modifier = Modifier.size(Paddings.medium))
        }

        // 직책 선택
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Paddings.xlarge),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.admin_role_selection_title),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            AdminPosition.values().forEach { position ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onAdminRoleChange(position)
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .size(Paddings.xlarge)
                            .padding(Paddings.none)
                    ) {
                        RadioButton(
                            selected = state.adminRole == position,
                            onClick = { viewModel.onAdminRoleChange(position) },
                        )
                    }
                    Spacer(modifier = Modifier.width(Paddings.medium))
                    Text(position.displayName)
                }
            }

            OutlinedTextField(
                value = state.customRole,
                onValueChange = { if (state.isCustomInput) viewModel.onCustomRoleChange(it) },
                label = { Text(stringResource(id = R.string.admin_custom_role_label)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .then(
                        if (!state.isCustomInput) Modifier.pointerInput(Unit) {
                            awaitPointerEventScope {
                                awaitPointerEvent()
                            }
                        } else Modifier
                    ),
                enabled = true,
                readOnly = !state.isCustomInput,
                textStyle = TextStyle(color = Color.Black)
            )
        }

        // 다음 버튼 (맨 아래 고정)
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LargeButton(
                text = stringResource(id = R.string.next_button_text),
                onClick = {
                    if (isNextEnabled) {
                        viewModel.onSignUpClick()
                    }
                }
            )
        }
    }
}