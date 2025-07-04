package com.example.presentation.login.member

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.presentation.component.LargeButton
import com.example.presentation.component.LargeDialog
import com.example.presentation.component.MediumButton
import com.example.presentation.component.MediumDialog
import com.example.presentation.login.MemberSignUpViewModel
import com.example.presentation.theme.Paddings
import com.example.presentation.theme.TebahTheme
import com.example.presentation.theme.TebahTypography
import com.example.presentation.theme.primary
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun MemberInfoScreen(
    viewModel: MemberSignUpViewModel,
    onNavigateToChurchSelect: () -> Unit
) {
    val state = viewModel.collectAsState().value

    var showPasswordMismatchDialog by remember { mutableStateOf(false) }
    var showIdDuplicateDialog by remember { mutableStateOf(false) }

    val isNextEnabled = state.name.isNotBlank() && state.id.isNotBlank() && state.password.isNotBlank() && state.repeatPassword.isNotBlank()
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
                text = "정보를",
                style = TebahTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
            Text(
                text = "입력해주세요",
                style = TebahTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
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
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("이름") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )
            OutlinedTextField(
                value = state.id,
                onValueChange = { viewModel.onIdChange(it) },
                label = { Text("아이디") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("비밀번호") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black),
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                value = state.repeatPassword,
                onValueChange = { viewModel.onRepeatPasswordChange(it) },
                label = { Text("비밀번호 확인") },
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
                onClick = {
                    if (state.password != state.repeatPassword) {
                        showPasswordMismatchDialog = true
                    } else {
                        // 아이디 중복 체크는 ViewModel에서 처리하는 게 좋음
                        onNavigateToChurchSelect()
                    }
                },
                text = "다음",
                backgroundColor = nextButtonColor
            )
        }

        if (showPasswordMismatchDialog) {
            MediumDialog(
                showDialog = showPasswordMismatchDialog,
                title = "비밀번호 일치 오류",
                content = "비밀번호를 다시 확인해주세요",
                buttonContent = "확인",
                onDismissRequest = { showPasswordMismatchDialog = false },
                onConfirm = { showPasswordMismatchDialog = false }
            )
        }

        if (showIdDuplicateDialog) {
            MediumDialog(
                showDialog = showIdDuplicateDialog,
                title = "아이디 중복",
                content = "이미 사용중인 아이디입니다\n다른 아이디를 입력해주세요",
                buttonContent = "확인",
                onDismissRequest = { showIdDuplicateDialog = false },
                onConfirm = { showIdDuplicateDialog = false }
            )
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun MemberInfoScreenPreview() {
//    TebahTheme {
//        MemberInfoScreen()
//    }
//}



