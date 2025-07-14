package com.example.presentation.member.screen.search

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.presentation.R
import com.example.presentation.shared.component.PostPreviewCard2
import com.example.presentation.write.PostData2
import com.example.presentation.common.theme.Paddings
import com.example.presentation.common.theme.TebahTypography
import com.example.presentation.common.theme.primary
import com.example.presentation.common.theme.third_01
import com.example.presentation.common.theme.third_03

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewSearchScreen(
    pullProgress: Float,
    onNavigateToSearchDetail: () -> Unit
) {
    val dummyImage = painterResource(R.drawable.profile_image)
    val dummyImage2 = painterResource(R.drawable.sample_image_01)
    val dummyImage3 = painterResource(R.drawable.sample_image_02)
    val posts = listOf(
        PostData2(
            isNotice = true,
            hasImages = true,
            profileImage = dummyImage,
            userId = "채널장1",
            postTime = "1분 전",
            previewText = "공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.",
            imageList = List(2) { dummyImage }
        ),
        PostData2(
            isNotice = false,
            hasImages = true,
            profileImage = dummyImage,
            userId = "채널장1",
            postTime = "10분 전",
            previewText = "오늘 날씨가 정말 좋네요~ 사진 몇 장 공유해요!공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.",
            imageList = List(3) { dummyImage2 }
        ),
        PostData2(
            isNotice = false,
            hasImages = false,
            profileImage = dummyImage,
            userId = "user_02",
            postTime = "1시간 전",
            previewText = "이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다."
        ),
        PostData2(
            isNotice = false,
            hasImages = false,
            profileImage = dummyImage,
            userId = "user_02",
            postTime = "1시간 전",
            previewText = "이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다."
        ),
        PostData2(
            isNotice = false,
            hasImages = true,
            profileImage = dummyImage,
            userId = "user_01",
            postTime = "10분 전",
            previewText = "오늘 날씨가 정말 좋네요~ 사진 몇 장 공유해요!공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.",
            imageList = listOf(dummyImage, dummyImage2, dummyImage3)
        ),
        PostData2(
            isNotice = false,
            hasImages = false,
            profileImage = dummyImage,
            userId = "user_02",
            postTime = "1시간 전",
            previewText = "이미지는 없지만 내용은 충실한 게시이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다."
        ),
        PostData2(
            isNotice = false,
            hasImages = true,
            profileImage = dummyImage,
            userId = "user_01",
            postTime = "10분 전",
            previewText = "오늘 날씨가 정말 좋네요~ 사진 몇 장이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확 공유해요!공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.",
            imageList = List(3) { dummyImage }
        ),
        PostData2(
            isNotice = true,
            hasImages = false,
            profileImage = dummyImage,
            userId = "user_02",
            postTime = "1시간 전",
            previewText = "이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확니다.공지사항입니다. 필수 확인 바랍니다."
        ),
        PostData2(
            isNotice = false,
            hasImages = false,
            profileImage = dummyImage,
            userId = "user_02",
            postTime = "1시간 전",
            previewText = "이미지는 없지만 내용은 충실한 게시글입니다.공지사항이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다."
        ),
        PostData2(
            isNotice = true,
            hasImages = false,
            profileImage = dummyImage,
            userId = "user_02",
            postTime = "1시간 전",
            previewText = "이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다."
        ),
        PostData2(
            isNotice = false,
            hasImages = true,
            profileImage = dummyImage,
            userId = "user_01",
            postTime = "10분 전",
            previewText = "오늘 날씨가 정말 좋네요~ 사진 몇 장 공유해요!공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.",
            imageList = List(3) { dummyImage }
        )
    )
    val dummyChannels = listOf(
        "책읽는사람들" to "독서를 좋아하는 사람들의 모임입니다.",
        "요리연구소" to "매주 새로운 레시피를 공유하는 요리 채널입니다.",
        "슬기로운개발생활" to "개발자들이 정보를 나누는 채널입니다.",
        "여행기록" to "국내외 여행지와 팁을 공유합니다.",
        "운동하는사람들" to "매일 운동 인증하고 같이 운동해요!"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.vector),
                    contentDescription = "로고 이미지",
                    tint = primary,
                    modifier = Modifier.size(48.dp + (pullProgress*50).dp)
                )
            }
        }

        stickyHeader {
            TebahSearchBarMock(
                onNavigateToSearchDetail = onNavigateToSearchDetail
            )
        }

        items(posts) { post ->
            PostPreviewCard2(
                isNotice = post.isNotice,
                hasImages = post.hasImages,
                profileImage = post.profileImage,
                userId = post.userId,
                postTime = post.postTime,
                previewText = post.previewText,
                imageList = post.imageList
            )

//            ChannelScrollRow(
//                channels = dummyChannels,
//                profileImage = dummyImage,
//                onAddClick = { }
//            )
        }
    }
}

@Composable
fun TebahSearchBarMock(
    modifier: Modifier = Modifier,
    placeholder: String = "검색",
    onNavigateToSearchDetail: () -> Unit
) {
    val height = 48.dp

    Column(modifier = modifier) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(Paddings.medium)
                .background(Color.White)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(Color.White),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Paddings.medium)
                    .clip(RoundedCornerShape(Paddings.xlarge))
                    .border(1.dp, third_01, RoundedCornerShape(Paddings.xlarge))
                    .background(third_03.copy(0.1f))
                    .clickable { onNavigateToSearchDetail() }
                    .padding(horizontal = Paddings.xlarge)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = primary
                )

                Spacer(modifier = Modifier.width(Paddings.medium))

                Text(
                    text = placeholder,
                    color = third_03,
                    style = TebahTypography.titleMedium,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(Paddings.medium)
                .background(Color.White)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun TebahSearchBarPreview() {
//    var query by remember { mutableStateOf("") }
//
//    NewSearchScreen()
//}