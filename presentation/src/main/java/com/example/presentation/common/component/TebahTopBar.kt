package com.example.presentation.common.component

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.common.theme.TebahTheme
import com.example.presentation.common.theme.TebahTypography
import com.example.presentation.common.theme.primary
import com.example.presentation.common.theme.third_01
import com.example.presentation.common.theme.third_02
import com.example.presentation.common.theme.third_03

@Composable
fun TebahTopBar(
    title: String,
    modifier: Modifier = Modifier,
    titleColor: Color = primary,
    titleFontWeight: FontWeight = FontWeight.Bold,
    iconsLeft: List<TopBarIconData> = emptyList(),
    iconsRight: List<TopBarIconData> = emptyList(),
    backgroundColor: Color = Color.White,
    dividerColor: Color = third_01,
    height: Dp = 48.dp,
    dividerVisible: Boolean = true
) {
    Column(modifier = modifier.background(backgroundColor)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            // Title
            Text(
                text = title,
                color = titleColor,
                fontWeight = titleFontWeight,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
                    .height(48.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.CenterVertically)
            )

            // Left icons row
            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                iconsLeft.forEach { iconData ->
                    IconButton(onClick = iconData.onClick, modifier = Modifier.size(40.dp)) {
                        Icon(
                            imageVector = iconData.icon,
                            contentDescription = iconData.contentDescription,
                            tint = iconData.tint ?: titleColor
                        )
                    }
                }
            }

            // Right icons row
            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                iconsRight.forEach { iconData ->
                    IconButton(onClick = iconData.onClick, modifier = Modifier.size(40.dp)) {
                        Icon(
                            imageVector = iconData.icon,
                            contentDescription = iconData.contentDescription,
                            tint = iconData.tint ?: titleColor
                        )
                    }
                }
            }
        }

        // Divider
        if(dividerVisible) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(dividerColor)
            )
        }
    }
}

data class TopBarIconData(
    val icon: ImageVector,
    val contentDescription: String? = null,
    val onClick: () -> Unit,
    val tint: Color? = null
)

@Preview(showBackground = true)
@Composable
fun TebahTopBarPreview() {
    TebahTheme {
        TebahTopBar(
            title = "새로운 글쓰기",
            iconsRight = listOf(
                TopBarIconData(
                    icon = Icons.Default.Close,
                    contentDescription = "닫기",
                    onClick = { /* TODO: 닫기 동작 */ }
                )
            ),
            dividerVisible = false
        )
    }
}