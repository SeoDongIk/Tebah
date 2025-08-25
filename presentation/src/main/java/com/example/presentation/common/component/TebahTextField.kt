package com.example.presentation.common.component

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.remember
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
    readOnly: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val labelColor = when {
        isError -> MaterialTheme.colorScheme.error
        value.isNotBlank() -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.outline
    }

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = { if (!readOnly) onValueChange(it) },
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
                else MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .let { base ->
                    if (onClick != null) {
                        base.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onClick() }
                    } else base
                },
            readOnly = readOnly,
            enabled = onClick == null, // 클릭 전용일 때는 false
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
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                // disabled 상태일 때도 일반 색 강제
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                disabledIndicatorColor = MaterialTheme.colorScheme.outline
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
            // 일반 입력 필드
            TebahTextField(
                value = "",
                onValueChange = {},
                label = "이름"
            )

            // 선택 전용 필드 (Region 같은 경우)
            TebahTextField(
                value = "서울",
                onValueChange = {},
                label = "지역",
                readOnly = true,
                onClick = { /* show region modal */ }
            )

            // 비밀번호
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