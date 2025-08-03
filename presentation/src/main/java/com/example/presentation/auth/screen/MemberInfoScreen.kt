package com.example.presentation.auth.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.example.presentation.R
import com.example.presentation.common.component.LargeButton
import com.example.presentation.common.component.MediumDialog
import com.example.presentation.auth.viewmodel.MemberSignUpViewModel
import com.example.presentation.auth.state.SignUpSideEffect
import com.example.presentation.common.theme.Paddings
import com.example.presentation.common.theme.primary

@Composable
fun MemberInfoScreen(
    viewModel: MemberSignUpViewModel,
    onNavigateToChurchSelect: () -> Unit
) {
    val state by viewModel.container.stateFlow.collectAsState()
    val sideEffect = viewModel.container.sideEffectFlow.collectAsState(initial = null).value

    LaunchedEffect(sideEffect) {
        when (sideEffect) {
            is SignUpSideEffect.NavigateToChurchSelect -> onNavigateToChurchSelect()
            else -> {}
        }
    }

    val isNextEnabled = viewModel.isNextEnabled
    val nextButtonColor = if (isNextEnabled) primary else Color.LightGray

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Paddings.layout_horizontal, vertical = Paddings.layout_vertical)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.member_info_title_line1),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = R.string.member_info_title_line2),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Paddings.xlarge),
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = state.name,
                onValueChange = viewModel::onNameChange,
                label = { Text(stringResource(R.string.label_name)) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )
            OutlinedTextField(
                value = state.id,
                onValueChange = viewModel::onIdChange,
                label = { Text(stringResource(R.string.label_id)) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text(stringResource(R.string.label_password)) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black),
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                value = state.repeatPassword,
                onValueChange = viewModel::onRepeatPasswordChange,
                label = { Text(stringResource(R.string.label_password_confirm)) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black),
                visualTransformation = PasswordVisualTransformation()
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LargeButton(
                onClick = { viewModel.onNextClicked() },
                text = stringResource(id = R.string.button_next),
                backgroundColor = nextButtonColor
            )
        }
    }

    if (state.isPasswordMismatchDialogShown) {
        MediumDialog(
            showDialog = true,
            title = stringResource(id = R.string.dialog_password_mismatch_title),
            content = stringResource(id = R.string.dialog_password_mismatch_content),
            buttonContent = stringResource(id = R.string.button_confirm),
            onDismissRequest = { viewModel.dismissPasswordMismatchDialog() },
            onConfirm = { viewModel.dismissPasswordMismatchDialog() }
        )
    }

    if (state.isIdDuplicateDialogShown) {
        MediumDialog(
            showDialog = true,
            title = stringResource(id = R.string.dialog_id_duplicate_title),
            content = stringResource(id = R.string.dialog_id_duplicate_content),
            buttonContent = stringResource(id = R.string.button_confirm),
            onDismissRequest = { viewModel.dismissIdDuplicateDialog() },
            onConfirm = { viewModel.dismissIdDuplicateDialog() }
        )
    }
}