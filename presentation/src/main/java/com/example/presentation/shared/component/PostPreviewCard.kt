package com.example.presentation.shared.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation.common.theme.Paddings
import com.example.presentation.common.theme.third_03

@Composable
fun PostPreviewCard2(
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
    onCheckClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
            )
    ) {
        PostHeader(profileImage, userId, postTime, previewText)

        if (hasImages && imageList.isNotEmpty()) {
            Spacer(modifier = Modifier.height(Paddings.medium))
            PostImages(imageList)
        }

        PostActions(
            isNotice = isNotice,
            onLikeClick = onLikeClick,
            onSaveClick = onSaveClick,
            onCheckClick = onCheckClick
        )

        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(third_03.copy(0.1f))
        )
    }
}

@Composable
fun PostHeader(profileImage: Painter, userId: String, postTime: String, previewText: String) {
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
            Row() {
                Text(text = userId, fontSize = 14.sp,color = Color.Black, fontWeight = FontWeight.Bold,
                    modifier = Modifier.alignBy(LastBaseline)
                )
                Spacer(modifier = Modifier.size(Paddings.small))
                Text(text = postTime, fontSize = 14.sp, color = Color.Gray,
                    modifier = Modifier.alignBy(LastBaseline)
                )
            }
            Spacer(modifier = Modifier.size(Paddings.small))
            Text(
                text = previewText,
                fontSize = 14.sp,
                color = Color.Black,
            )
        }

    }
}

@Composable
fun PostImages(images: List<Painter>) {
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
fun PostActions(
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