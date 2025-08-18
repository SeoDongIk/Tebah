package com.example.presentation.common.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.common.theme.Paddings
import com.example.presentation.common.theme.TebahTheme

@Composable
fun TebahTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    // 상태에 따라 Label 색 결정
    val labelColor = when {
        isError -> MaterialTheme.colorScheme.error
        value.isNotBlank() -> MaterialTheme.colorScheme.primary // 값 있으면 무조건 Navy
        else -> MaterialTheme.colorScheme.outline // 비어있으면 Gray
    }

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label,
                    color = labelColor,
                    style = MaterialTheme.typography.labelLarge
                )
            },
            isError = isError,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            textStyle = TextStyle(
                color = if (isError) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.onSurface // 기본 Black
            ),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                // 텍스트
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                errorTextColor = MaterialTheme.colorScheme.error,
                // 배경
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                errorContainerColor = MaterialTheme.colorScheme.surface,
                // 커서
                cursorColor = MaterialTheme.colorScheme.primary,
                // 테두리
                focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Navy
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline, // Gray
                errorIndicatorColor = MaterialTheme.colorScheme.error // Orange
                // Label 관련 색상은 무시 (직접 강제했으므로)
            )
        )

        if (isError && !errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = Paddings.xlarge, top = Paddings.small)
            )
        }
    }
}

@Preview(
    name = "TextField States Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun TebahTextFieldLightPreview() {
    TebahTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 빈 상태
            TebahTextField(
                value = "",
                onValueChange = {},
                label = "이름"
            )

            // 입력 중 (정상)
            TebahTextField(
                value = "ALOdoi10298",
                onValueChange = {},
                label = "아이디"
            )

            // 입력 중 (비밀번호)
            TebahTextField(
                value = "********",
                onValueChange = {},
                label = "비밀번호",
                visualTransformation = PasswordVisualTransformation()
            )

            // 에러 상태
            TebahTextField(
                value = "********",
                onValueChange = {},
                label = "비밀번호 확인",
                isError = true,
                errorMessage = "비밀번호가 일치하지 않습니다.",
                visualTransformation = PasswordVisualTransformation()
            )
        }
    }
}

@Preview(
    name = "TextField States Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun TebahTextFieldDarkPreview() {
    TebahTextFieldLightPreview()
}