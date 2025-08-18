package com.example.presentation.auth.screen

import android.content.Intent
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.UserRole
import com.example.presentation.R
import com.example.presentation.auth.viewmodel.SignInViewModel
import com.example.presentation.common.component.LargeButton
import com.example.presentation.common.component.MediumButton
import com.example.presentation.admin.AdminActivity
import com.example.presentation.member.MemberActivity
import com.example.presentation.auth.state.SignInSideEffect
import com.example.presentation.auth.state.SignInState
import com.example.presentation.common.component.TebahTextField
import com.example.presentation.common.theme.Paddings
import com.example.presentation.common.theme.TebahTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    onNavigateToRole: () -> Unit,
) {
    val context = LocalContext.current
    val state by viewModel.container.stateFlow.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collectLatest { effect ->
            when (effect) {
                is SignInSideEffect.Toast -> {
                    Toast.makeText(
                        context,
                        context.getString(effect.messageRes),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is SignInSideEffect.NavigateToMainActivity -> {
                    val targetActivity = if (effect.role == UserRole.ADMIN) {
                        AdminActivity::class.java
                    } else {
                        MemberActivity::class.java
                    }
                    val intent = Intent(context, targetActivity).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    SignInContent(
        state = state,
        onIdChange = viewModel::onIdChange,
        onPasswordChange = viewModel::onPasswordChange,
        onAutoLoginChange = viewModel::onAutoLoginChange,
        onSignInClick = viewModel::onSignInClick,
        onNavigateToRole = onNavigateToRole
    )
}

@Composable
private fun SignInContent(
    state: SignInState,
    onIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onAutoLoginChange: (Boolean) -> Unit,
    onSignInClick: () -> Unit,
    onNavigateToRole: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Paddings.layout_horizontal, vertical = Paddings.layout_vertical)
    ) {
        // 로고
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            TebahLogo()
        }

        // 입력 폼
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Paddings.xlarge),
            horizontalAlignment = Alignment.Start
        ) {
            TebahTextField(
                value = state.id,
                onValueChange = onIdChange,
                label = stringResource(R.string.label_id),
                modifier = Modifier.fillMaxWidth(),
                isError = state.idError != null,
                errorMessage = state.idError?.let { stringResource(it) }
            )

            TebahTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                label = stringResource(R.string.label_password),
                modifier = Modifier.fillMaxWidth(),
                isError = state.passwordError != null,
                errorMessage = state.passwordError?.let { stringResource(it) },
                visualTransformation = PasswordVisualTransformation()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Paddings.medium)
                    .wrapContentWidth(Alignment.Start)
            ) {
                Checkbox(
                    checked = state.autoLogin,
                    onCheckedChange = onAutoLoginChange,
                    modifier = Modifier.size(Paddings.extra)
                )
                Spacer(modifier = Modifier.width(Paddings.medium))
                Text(
                    text = stringResource(R.string.label_save_login),
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center
                )
            }
        }

        // 로그인 버튼, 회원가입 버튼
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginButtonSection(
                onClick = onSignInClick,
                onBrowseServiceClick = onNavigateToRole,
                modifier = Modifier.fillMaxWidth(),
                enabled = state.isLoginEnabled
            )
        }
    }
}

@Composable
fun TebahLogo(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Paddings.medium), // 글자 간격 넓힘
        verticalAlignment = Alignment.CenterVertically
    ) {
        listOf(
            R.drawable.ic_t,
            R.drawable.ic_e,
            R.drawable.ic_b,
            R.drawable.ic_a,
            R.drawable.ic_h
        ).forEach { resId ->
            Image(
                painter = painterResource(id = resId),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
private fun LoginButtonSection(
    onClick: () -> Unit,
    onBrowseServiceClick: () -> Unit,
    modifier: Modifier,
    enabled: Boolean
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LargeButton(
            text = stringResource(R.string.button_login),
            onClick = onClick,
            enabled = enabled
        )
        BrowseServiceTextButton(
            modifier = Modifier,
            onClick = onBrowseServiceClick
        )
    }
}

@Composable
private fun BrowseServiceTextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Text(
        text = stringResource(R.string.button_signup),
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(vertical = Paddings.xlarge),
        color =  MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Center
    )
}

@Preview(
    name = "SignInScreen - Default",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SignInScreenDefaultPreview() {
    TebahTheme {
        SignInContent(
            state = SignInState(
                id = "",
                password = "",
                autoLogin = false,
                isLoginEnabled = false
            ),
            onIdChange = {},
            onPasswordChange = {},
            onAutoLoginChange = {},
            onSignInClick = {},
            onNavigateToRole = {}
        )
    }
}

@Preview(
    name = "SignInScreen - Error State",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SignInScreenErrorPreview() {
    TebahTheme {
        SignInContent(
            state = SignInState(
                id = "wrong-email",
                password = "123",
                idError = R.string.error_invalid_email,
                passwordError = R.string.error_password_too_short,
                autoLogin = false,
                isLoginEnabled = false
            ),
            onIdChange = {},
            onPasswordChange = {},
            onAutoLoginChange = {},
            onSignInClick = {},
            onNavigateToRole = {}
        )
    }
}

@Preview(
    name = "SignInScreen - Loading",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SignInScreenLoadingPreview() {
    TebahTheme {
        SignInContent(
            state = SignInState(
                id = "dongik@example.com",
                password = "123456",
                autoLogin = true,
                isLoginEnabled = true,
                isLoading = true
            ),
            onIdChange = {},
            onPasswordChange = {},
            onAutoLoginChange = {},
            onSignInClick = {},
            onNavigateToRole = {}
        )
    }
}