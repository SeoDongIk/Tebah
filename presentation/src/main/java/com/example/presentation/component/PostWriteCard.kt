package com.example.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.theme.Paddings
import com.example.presentation.theme.primary
import com.example.presentation.theme.third_01
import com.example.presentation.theme.third_03
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun PostWriteCard(
    modifier: Modifier = Modifier,
    isNotice: Boolean,
    hasImages: Boolean,
    profileImage: Painter,
    userId: String,
    postTime: String,
    previewText: String,
    imageList: List<Painter> = emptyList(),
    onLikeClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    onCheckClick: () -> Unit = {},
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
            )
    ) {
        PostHeader2(profileImage, userId, previewText, onValueChange, focusRequester)

        if (hasImages && imageList.isNotEmpty()) {
            Spacer(modifier = Modifier.height(Paddings.medium))
            PostImages2(imageList)
        }
    }

}

@Composable
fun PostHeader2(profileImage: Painter, userId: String, previewText: String, onValueChange: (String) -> Unit, focusRequester: FocusRequester) {
    val regions = listOf(
        "IT 선교소모임",
        "진식순",
        "혜원순",
        "은혜광성교회 청년2부",
        "1청"
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .padding(top = Paddings.medium, start = Paddings.medium, end = Paddings.medium)
    ) {
        Image(
            painter = profileImage,
            contentDescription = "Profile",
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .align(Alignment.Top)
                .background(Color.Gray, shape = CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.size(Paddings.medium))
        Column(
            modifier = Modifier
                .align(Alignment.Top),
        ){
            Row {
                Text(text = userId, fontSize = 14.sp,color = Color.Black, fontWeight = FontWeight.Bold,
                    modifier = Modifier.alignBy(LastBaseline)
                )
                Spacer(modifier = Modifier.size(Paddings.small))
                Text(text = "> 채널 추가", fontSize = 14.sp, color = third_03, fontWeight = FontWeight.Bold,
                    modifier = Modifier.alignBy(LastBaseline)
                        .clickable {

                        }
                )
            }
            FlowRow(
                modifier = Modifier.padding(vertical = Paddings.medium),
                mainAxisSpacing = Paddings.small, // 조금 더 조밀하게
                crossAxisSpacing = Paddings.small
            ) {
                regions.forEach { region ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .wrapContentSize()
                            .border(
                                width = 1.dp,
                                color = third_01.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = region,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "삭제",
                            modifier = Modifier
                                .size(14.dp)
                                .clickable { /* TODO: X 클릭 동작 */ },
                            tint = primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.size(Paddings.small))

            BasicTextField(
                value = previewText,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth().focusRequester(focusRequester)
                ,
                singleLine = false,
                cursorBrush = SolidColor(Color.Black),
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Black
                ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        innerTextField()
                    }
                }
            )
        }

    }
}

@Composable
fun PostImages2(images: List<Painter>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(Paddings.medium)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(Paddings.medium)
    ) {
        Spacer(modifier = Modifier.size(36.dp))
        images.forEach { image ->
            Image(
                painter = image,
                contentDescription = "Post image",
                modifier = Modifier
                    .width(160.dp)
                    .fillMaxHeight()
                    .background(Color.LightGray, shape = RoundedCornerShape(Paddings.medium))
            )
        }
    }
}

@Composable
fun PostActions2(
    isNotice: Boolean,
    onLikeClick: () -> Unit,
    onSaveClick: () -> Unit,
    onCheckClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 52.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isNotice) {
            Box(
                modifier = Modifier.size(40.dp), // 아이콘 크기
                contentAlignment = Alignment.CenterStart
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Check",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp) // Material3 아이콘 크기
                )
            }
        }
        Box(
            modifier = Modifier.size(40.dp), // 아이콘 크기
            contentAlignment = Alignment.CenterStart
        ) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Like",
                tint = Color.Black,
                modifier = Modifier.size(24.dp) // Material3 아이콘 크기
            )
        }
        Box(
            modifier = Modifier.size(40.dp), // 아이콘 크기
            contentAlignment = Alignment.CenterStart
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Save",
                tint = Color.Black,
                modifier = Modifier.size(24.dp) // Material3 아이콘 크기
            )
        }
    }
}