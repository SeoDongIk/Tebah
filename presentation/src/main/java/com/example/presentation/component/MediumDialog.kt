package com.example.presentation.component

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.presentation.theme.Paddings
import com.example.presentation.theme.TebahTypography
import com.example.presentation.theme.primary

@Composable
fun MediumDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    title: String,
    content: String,
    buttonContent: String,
    onConfirm: () -> Unit
) {
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
                            style = TebahTypography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                        Text(
                            text = content,
                            style = TebahTypography.titleMedium,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.size(Paddings.medium))
                        Button(
                            onClick = {
                                onConfirm
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(percent = 20),
                            colors = ButtonDefaults.buttonColors(containerColor = primary)
                        ) {
                            Text(buttonContent, style = TebahTypography.titleMedium.copy(fontWeight = FontWeight.Bold))
                        }
                    }
                }
            }
        }
    }
}