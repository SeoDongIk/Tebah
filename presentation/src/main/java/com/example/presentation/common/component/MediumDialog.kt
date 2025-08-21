package com.example.presentation.common.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.presentation.common.theme.Paddings
import com.example.presentation.common.theme.TebahTheme

@Composable
fun MediumDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    title: String,
    content: String,
    buttonContent: String,
    onConfirm: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = 0.4f, stiffness = 400f),
        label = ""
    )

    if(showDialog) {
        Dialog(onDismissRequest = onDismissRequest) {
            BoxWithConstraints {
                val maxWidth = this.maxWidth
                Surface(
                    shape = RoundedCornerShape(percent = 8),
                    color = Color.White,
                    modifier = Modifier
                        .width(maxWidth)
                ) {
                    Column(
                        modifier = Modifier.padding(Paddings.xlarge),
                        verticalArrangement = Arrangement.spacedBy(Paddings.large),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.size(Paddings.small))
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                        Text(
                            text = content,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.size(Paddings.medium))
                        Button(
                            onClick = onConfirm,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .scale(scale),
                            shape = RoundedCornerShape(percent = 20),
                            interactionSource = interactionSource,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(buttonContent, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MediumDialogPreview() {
    TebahTheme {
        MediumDialog(
            showDialog = true,
            onDismissRequest = { /* Dismiss logic */ },
            title = "안내",
            content = "정말로 로그아웃 하시겠습니까?",
            buttonContent = "확인",
            onConfirm = { /* Confirm logic */ }
        )
    }
}