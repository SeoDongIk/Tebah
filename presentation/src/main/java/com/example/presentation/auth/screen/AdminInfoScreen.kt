package com.example.presentation.auth.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.AdminPosition
import com.example.presentation.R
import com.example.presentation.auth.state.SignUpSideEffect
import com.example.presentation.auth.viewmodel.AdminSignUpViewModel
import com.example.presentation.common.component.LargeButton
import com.example.presentation.common.component.MediumDialog
import com.example.presentation.common.component.TebahTextField
import com.example.presentation.common.component.TebahTopBar
import com.example.presentation.common.extension.displayName
import com.example.presentation.common.theme.Paddings
import com.example.presentation.common.theme.TebahTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AdminInfoScreen(
    viewModel: AdminSignUpViewModel,
    onNavigateToComplete: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collectLatest { effect ->
            when (effect) {
                is SignUpSideEffect.Toast -> {
                    Toast.makeText(context, context.getString(effect.messageRes), Toast.LENGTH_SHORT).show()
                }
                SignUpSideEffect.NavigateToCompleteScreen -> onNavigateToComplete()
                SignUpSideEffect.NavigateToChurchSelect -> Unit
                is SignUpSideEffect.NavigateToMain -> Unit
            }
        }
    }

    Scaffold(
        topBar = { TebahTopBar(onBackClick = onBackClick) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        AdminInfoBody(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(
                    horizontal = Paddings.layout_horizontal,
                    vertical = Paddings.layout_vertical
                ),
            titleLine1 = stringResource(R.string.admin_info_title_1),
            titleLine2 = stringResource(R.string.admin_info_title_2),

            name = state.name,
            id = state.id,
            password = state.password,
            repeatPassword = state.repeatPassword,

            nameError = state.nameError,
            idError = state.idError,
            passwordError = state.passwordError,
            repeatPasswordError = state.repeatPasswordError,

            adminRole = state.adminRole,
            isCustomInput = state.isCustomInput,
            customRole = state.customRole,
            customRoleError = state.customRoleError,

            isSigningUp = state.isSigningUp,
            isUserInfoValid = state.isUserInfoValid,

            onAdminNameChange = viewModel::onAdminNameChange,
            onIdChange = viewModel::onIdChange,
            onPasswordChange = viewModel::onPasswordChange,
            onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
            onAdminRoleChange = viewModel::onAdminRoleChange,
            onCustomRoleChange = viewModel::onCustomRoleChange,
            onSubmit = viewModel::onSignUpClick
        )
    }

    state.dialog?.let { d ->
        MediumDialog(
            showDialog = true,
            title = stringResource(d.titleRes),
            content = stringResource(d.messageRes),
            buttonContent = stringResource(d.confirmTextRes),
            onConfirm = { viewModel.dismissDialog() },
            onDismissRequest = { viewModel.dismissDialog() }
        )
    }
}

/* ---------- Body ---------- */

@Composable
private fun AdminInfoBody(
    modifier: Modifier = Modifier,
    titleLine1: String,
    titleLine2: String,
    name: String,
    id: String,
    password: String,
    repeatPassword: String,
    nameError: Int?,
    idError: Int?,
    passwordError: Int?,
    repeatPasswordError: Int?,
    adminRole: AdminPosition,
    isCustomInput: Boolean,
    customRole: String,
    customRoleError: Int?,
    isSigningUp: Boolean,
    isUserInfoValid: Boolean,
    onAdminNameChange: (String) -> Unit,
    onIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onAdminRoleChange: (AdminPosition) -> Unit,
    onCustomRoleChange: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    Column(modifier) {
        TitleSection(
            titleLine1 = titleLine1,
            titleLine2 = titleLine2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Paddings.medium)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Paddings.xlarge)
        ) {
            FormSection(
                name = name,
                id = id,
                password = password,
                repeatPassword = repeatPassword,
                nameError = nameError,
                idError = idError,
                passwordError = passwordError,
                repeatPasswordError = repeatPasswordError,
                onAdminNameChange = onAdminNameChange,
                onIdChange = onIdChange,
                onPasswordChange = onPasswordChange,
                onRepeatPasswordChange = onRepeatPasswordChange
            )

            RoleSection(
                adminRole = adminRole,
                isCustomInput = isCustomInput,
                customRole = customRole,
                customRoleError = customRoleError,
                onAdminRoleChange = onAdminRoleChange,
                onCustomRoleChange = onCustomRoleChange
            )
        }

        ButtonBar(
            enabled = isUserInfoValid,
            isLoading = isSigningUp,
            onSubmit = onSubmit,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Paddings.medium)
        )
    }
}

/* ---------- Sections ---------- */

@Composable
private fun TitleSection(
    titleLine1: String,
    titleLine2: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = titleLine1,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = titleLine2,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
private fun FormSection(
    name: String,
    id: String,
    password: String,
    repeatPassword: String,
    nameError: Int?,
    idError: Int?,
    passwordError: Int?,
    repeatPasswordError: Int?,
    onAdminNameChange: (String) -> Unit,
    onIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Paddings.xlarge),
        horizontalAlignment = Alignment.Start
    ) {
        // 섹션 제목
        Text(
            text = stringResource(R.string.admin_info_section_title), // "관리자 정보"
            style = MaterialTheme.typography.titleMedium,
        )

        TebahTextField(
            value = name,
            onValueChange = onAdminNameChange,
            label = stringResource(R.string.admin_name_label),
            isError = nameError != null,
            errorMessage = nameError?.let { stringResource(it) }
        )

        TebahTextField(
            value = id,
            onValueChange = onIdChange,
            label = stringResource(R.string.admin_id_label),
            isError = idError != null,
            errorMessage = idError?.let { stringResource(it) }
        )

        TebahTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = stringResource(R.string.admin_password_label),
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError != null,
            errorMessage = passwordError?.let { stringResource(it) }
        )

        TebahTextField(
            value = repeatPassword,
            onValueChange = onRepeatPasswordChange,
            label = stringResource(R.string.admin_password_confirm_label),
            visualTransformation = PasswordVisualTransformation(),
            isError = repeatPasswordError != null,
            errorMessage = repeatPasswordError?.let { stringResource(it) }
        )
    }
}

@Composable
private fun RoleSection(
    adminRole: AdminPosition,
    isCustomInput: Boolean,
    customRole: String,
    customRoleError: Int?,
    onAdminRoleChange: (AdminPosition) -> Unit,
    onCustomRoleChange: (String) -> Unit
) {
    val customFocusRequester = FocusRequester()
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(isCustomInput) {
        if (isCustomInput) {
            customFocusRequester.requestFocus()
            keyboard?.show()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Paddings.xsmall),
        horizontalAlignment = Alignment.Start
    ) {
        // 섹션 제목
        Text(
            text = stringResource(R.string.admin_role_selection_title), // "직책 선택"
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = Paddings.small)
        )

        AdminPosition.values().forEach { position ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAdminRoleChange(position) }
            ) {
                RadioButton(
                    selected = adminRole == position,
                    onClick = { onAdminRoleChange(position) },
                )
                Spacer(modifier = Modifier.width(Paddings.medium))
                Text(text = position.displayName(LocalContext.current))
            }
        }

        TebahTextField(
            value = customRole,
            onValueChange = onCustomRoleChange,
            label = stringResource(R.string.admin_custom_role_label),
            readOnly = !isCustomInput,
            onClick = if (!isCustomInput) {
                { onAdminRoleChange(AdminPosition.CUSTOM) }
            } else null,
            isError = customRoleError != null,
            errorMessage = customRoleError?.let { stringResource(it) },
            modifier = Modifier.focusRequester(customFocusRequester)
        )

        Spacer(modifier = Modifier.height(Paddings.large))
    }
}

@Composable
private fun ButtonBar(
    enabled: Boolean,
    isLoading: Boolean,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LargeButton(
            text = stringResource(R.string.next_button_text),
            onClick = onSubmit,
            enabled = enabled,
            isLoading = isLoading
        )
    }
}

/* ---------- Preview ---------- */

@Preview(name = "AdminInfo - Default", showBackground = true)
@Composable
private fun AdminInfoBodyPreviewDefault() {
    TebahTheme {
        AdminInfoBody(
            titleLine1 = "관리자 정보를",
            titleLine2 = "입력해 주세요",
            name = "홍길동",
            id = "test@example.com",
            password = "",
            repeatPassword = "",
            nameError = null,
            idError = null,
            passwordError = null,
            repeatPasswordError = null,
            adminRole = AdminPosition.PASTOR,
            isCustomInput = false,
            customRole = "",
            customRoleError = null,
            isSigningUp = false,
            isUserInfoValid = true,
            onAdminNameChange = {},
            onIdChange = {},
            onPasswordChange = {},
            onRepeatPasswordChange = {},
            onAdminRoleChange = {},
            onCustomRoleChange = {},
            onSubmit = {}
        )
    }
}