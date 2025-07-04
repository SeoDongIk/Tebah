package com.example.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation.theme.Paddings
import com.example.presentation.theme.third_03

@Composable
fun MiniCommentItem() {
    val dummyImage = painterResource(R.drawable.profile_image)

    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.size(Paddings.large))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Paddings.medium, vertical = Paddings.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 텍스트 영역
            Column {
                Row {
                    Text(
                        text = "MalcongMalcom",
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 18.sp
                    )
//                    Spacer(modifier = Modifier.size(Paddings.medium))
//                    Box(
//                        modifier = Modifier
//                            .align(Alignment.CenterVertically)
//                            .background(color = Color(0xFF007AFF), shape = RoundedCornerShape(4.dp))
//                            .padding(horizontal = 4.dp, vertical = 1.dp) // 최소 여백
//                    ) {
//                        Text(
//                            text = "공식",
//                            fontSize = 8.sp,
//                            color = Color.White,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
                }

                Text(
                    text = "내가 작성한 댓글입니다. 내가 작성한 댓글입니다.",
                    fontSize = 14.sp,
                    color = Color.Black.copy(0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )
                Text(
                    text = "23시간 전",
                    fontSize = 14.sp,
                    color = third_03,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.size(Paddings.large))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(third_03.copy(0.2f))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MiniCommentlItemPreview() {
    MiniCommentItem()
}