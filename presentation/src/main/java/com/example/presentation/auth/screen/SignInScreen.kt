package com.example.presentation.auth.screen

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.UserRole
import com.example.presentation.R
import com.example.presentation.auth.viewmodel.SignInViewModel
import com.example.presentation.common.component.LargeButton
import com.example.presentation.common.component.MediumButton
import com.example.presentation.admin.AdminActivity
import com.example.presentation.member.MemberActivity
import com.example.presentation.auth.state.SignInSideEffect
import com.example.presentation.common.theme.Paddings
import com.example.presentation.common.theme.TebahTypography

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    onNavigateToRole: () -> Unit,
) {
    val context = LocalContext.current
    val state by viewModel.container.stateFlow.collectAsState()
    val sideEffect by viewModel.container.sideEffectFlow.collectAsState(initial = null)
    val isLoginEnabled = state.isLoginEnabled
    val loginButtonColor = state.loginButtonColor

    LaunchedEffect(sideEffect) {
        sideEffect?.let { effect ->
            when (effect) {
                is SignInSideEffect.Toast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Paddings.layout_horizontal, vertical = Paddings.layout_vertical)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.vector),
                contentDescription = stringResource(R.string.cd_logo)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Paddings.xlarge),
            horizontalAlignment = Alignment.Start
        ) {
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
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
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
                    onCheckedChange = viewModel::onAutoLoginChange,
                    modifier = Modifier.size(Paddings.extra)
                )
                Spacer(modifier = Modifier.width(Paddings.medium))
                Text(
                    text = stringResource(R.string.label_save_login),
                    style = TebahTypography.labelMedium,
                    textAlign = TextAlign.Center
                )
            }

            MediumButton(
                text = stringResource(R.string.button_signup),
                onClick = onNavigateToRole,
                modifier = Modifier.align(Alignment.Start)
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
                text = stringResource(R.string.button_login),
                onClick = { viewModel.onSignInClick() },
                backgroundColor = loginButtonColor,
                enabled = isLoginEnabled
            )
        }
    }
}