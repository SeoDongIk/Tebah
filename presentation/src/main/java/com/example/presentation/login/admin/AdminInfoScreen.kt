package com.example.presentation.login.admin

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.presentation.component.LargeButton
import com.example.presentation.login.AdminSignUpViewModel
import com.example.presentation.theme.Paddings
import com.example.presentation.theme.TebahTheme
import com.example.presentation.theme.TebahTypography
import com.example.presentation.theme.primary

@Composable
fun AdminInfoScreen(
    viewModel: AdminSignUpViewModel,
    onNavigateToComplete: () -> Unit
) {
    var AdminName by remember { mutableStateOf("") }
    var AdminId by remember { mutableStateOf("") }
    var AdminPassword by remember { mutableStateOf("") }
    var AdminPasswordChecked by remember { mutableStateOf("") }

    val isNextEnabled = AdminName.isNotBlank() && AdminId.isNotBlank() && AdminPassword.isNotBlank() && AdminPasswordChecked.isNotBlank()
    val nextButtonColor = if (isNextEnabled) primary else Color.LightGray

    var selectedPosition by remember { mutableStateOf<String?>(null) }
    var customPosition by remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }
    val isCustomInput = selectedPosition == "직접입력"

    LaunchedEffect(selectedPosition) {
        if (isCustomInput) {
            focusRequester.requestFocus()
        } else {
            customPosition = ""
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = Paddings.layout_horizontal,
                vertical = Paddings.layout_vertical)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "관리자 정보를",
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
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Paddings.medium),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.size(Paddings.medium))
            Text(
                text = "관리자 정보",
                style = TebahTypography.titleMedium,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = AdminName,
                onValueChange = { AdminName = it },
                label = { Text("이름") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )
            OutlinedTextField(
                value = AdminId,
                onValueChange = { AdminId = it },
                label = { Text("아이디") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )

            OutlinedTextField(
                value = AdminPassword,
                onValueChange = { AdminPassword = it },
                label = { Text("비밀번호") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )
            OutlinedTextField(
                value = AdminPasswordChecked,
                onValueChange = { AdminPasswordChecked = it },
                label = { Text("비밀번호 확인") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black)
            )
            Spacer(modifier = Modifier.size(Paddings.medium))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Paddings.xlarge),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "직책 선택",
                style = TebahTypography.titleMedium,
                textAlign = TextAlign.Center
            )
            listOf("목사", "관리자", "직접입력").forEach { position ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedPosition = position
                            if (position != "직접입력") {
                                customPosition = ""
                            }
                        }
                ) {
                    Box(modifier = Modifier.size(Paddings.xlarge)
                        .padding(Paddings.none)) {
                        RadioButton(
                            selected = selectedPosition == position,
                            onClick = { selectedPosition = position
                                if (position != "직접입력") customPosition = ""
                                      },
                            modifier = Modifier
                        )
                    }
                    Spacer(modifier = Modifier.width(Paddings.medium))
                    Text(position)
                }
            }
            OutlinedTextField(
                value = customPosition,
                onValueChange = { if(isCustomInput) customPosition = it },
                label = { Text("직접 입력") },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .then(
                        if (!isCustomInput) Modifier.pointerInput(Unit) {
                            awaitPointerEventScope {
                                awaitPointerEvent()
                            }
                        } else Modifier
                    ),
                enabled = true,
                readOnly = !isCustomInput,
                textStyle = TextStyle(color = Color.Black)
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
                text = "다음",
                onClick = onNavigateToComplete,
                backgroundColor = nextButtonColor
            )
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun AdminInfoScreenPreview() {
//    TebahTheme {
//        AdminInfoScreen()
//    }
//}