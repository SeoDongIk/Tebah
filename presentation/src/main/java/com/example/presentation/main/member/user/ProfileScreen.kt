package com.example.presentation.main.member.user

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation.component.MiniChannelItem
import com.example.presentation.component.MiniCommentItem
import com.example.presentation.component.PostPreviewCard
import com.example.presentation.component.TebahTopBar
import com.example.presentation.component.TopBarIconData
import com.example.presentation.model.PostData
import com.example.presentation.theme.Paddings
import com.example.presentation.theme.TebahTheme
import com.example.presentation.theme.TebahTypography
import com.example.presentation.theme.primary
import com.example.presentation.theme.third_01
import com.example.presentation.theme.third_03

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
) {
    var selectedTab by remember { mutableStateOf(0) }
    var selectedSubTab01 by remember { mutableStateOf(0) }
    var selectedSubTab02 by remember { mutableStateOf(0) }
    val tabs2 = listOf("게시글", "채널", "내 활동")
    val tabContents = listOf(
        listOf("게시글"),
        listOf("내가 만든 채널", "구독한 채널"),
        listOf("내가 쓴 댓글", "좋아요", "저장")
    )

    val dummyImage = painterResource(R.drawable.profile_image)
    val dummyImage2 = painterResource(R.drawable.sample_image_01)
    val dummyImage3 = painterResource(R.drawable.sample_image_02)
    val posts = listOf(
        PostData(
            isNotice = true,
            hasImages = true,
            profileImage = dummyImage,
            userId = "채널장1",
            postTime = "1분 전",
            previewText = "공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.",
            imageList = List(2) { dummyImage }
        ),
        PostData(
            isNotice = false,
            hasImages = true,
            profileImage = dummyImage,
            userId = "채널장1",
            postTime = "10분 전",
            previewText = "오늘 날씨가 정말 좋네요~ 사진 몇 장 공유해요!공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.",
            imageList = List(3) { dummyImage2 }
        ),
        PostData(
            isNotice = false,
            hasImages = false,
            profileImage = dummyImage,
            userId = "user_02",
            postTime = "1시간 전",
            previewText = "이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다."
        ),
        PostData(
            isNotice = false,
            hasImages = false,
            profileImage = dummyImage,
            userId = "user_02",
            postTime = "1시간 전",
            previewText = "이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다."
        ),
        PostData(
            isNotice = false,
            hasImages = true,
            profileImage = dummyImage,
            userId = "user_01",
            postTime = "10분 전",
            previewText = "오늘 날씨가 정말 좋네요~ 사진 몇 장 공유해요!공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.",
            imageList = listOf(dummyImage, dummyImage2, dummyImage3)
        ),
        PostData(
            isNotice = false,
            hasImages = false,
            profileImage = dummyImage,
            userId = "user_02",
            postTime = "1시간 전",
            previewText = "이미지는 없지만 내용은 충실한 게시이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다."
        ),
        PostData(
            isNotice = false,
            hasImages = true,
            profileImage = dummyImage,
            userId = "user_01",
            postTime = "10분 전",
            previewText = "오늘 날씨가 정말 좋네요~ 사진 몇 장이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확 공유해요!공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.",
            imageList = List(3) { dummyImage }
        ),
        PostData(
            isNotice = true,
            hasImages = false,
            profileImage = dummyImage,
            userId = "user_02",
            postTime = "1시간 전",
            previewText = "이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확니다.공지사항입니다. 필수 확인 바랍니다."
        ),
        PostData(
            isNotice = false,
            hasImages = false,
            profileImage = dummyImage,
            userId = "user_02",
            postTime = "1시간 전",
            previewText = "이미지는 없지만 내용은 충실한 게시글입니다.공지사항이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다."
        ),
        PostData(
            isNotice = true,
            hasImages = false,
            profileImage = dummyImage,
            userId = "user_02",
            postTime = "1시간 전",
            previewText = "이미지는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다."
        ),
        PostData(
            isNotice = false,
            hasImages = true,
            profileImage = dummyImage,
            userId = "user_01",
            postTime = "10분 전",
            previewText = "오늘 날씨가 정말 좋네요~ 사진 몇 장 공유해요!공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다.",
            imageList = List(3) { dummyImage }
        )
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
                    modifier = Modifier.size(48.dp + (0*50).dp)
                )
            }

            TebahTopBar(
                title = "",
                iconsRight = listOf(
                    TopBarIconData(
                        icon = Icons.Default.Settings,
                        contentDescription = "설정",
                        onClick = {
                            // 설정 클릭 시 동작
                        }
                    )
                ),
                dividerVisible = false
            )
            Column(
                modifier = Modifier
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Paddings.layout_horizontal),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 동그란 프로필 이미지
                    Image(
                        painter = dummyImage,
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.Gray),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // 텍스트 영역
                    Column(
                        modifier = Modifier.alignBy(LastBaseline)
                    ) {
                        Text(
                            text = "MalcongMalcom",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "안녕하세요! 개발자입니다.안녕하세요! 개발자 개발자입니다.안녕하세요입니다.안녕하세요!",
                            fontSize = 14.sp,
                            color = third_03,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 18.sp
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(vertical = 16.dp, horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 1) 팔로워
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(2f)
                    ) {
                        Text(text = "팔로워", fontSize = 14.sp, color = Color.Black)
                        Text(text = "440", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    }

                    // 2) 팔로잉
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(2f)
                    ) {
                        Text(text = "팔로잉", fontSize = 14.sp, color = Color.Black)
                        Text(text = "12", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    }

                    // 3) 게시글
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(2f)
                    ) {
                        Text(text = "게시글", fontSize = 14.sp, color = Color.Black)
                        Text(text = "140", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    }

                    Box(
                        modifier = Modifier
                            .weight(3f)
                            .padding(start = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = { /* TODO: 편집 클릭 */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            shape = RoundedCornerShape(24.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                            modifier = Modifier
                                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(24.dp))
                        ) {
                            Text(text = "프로필 편집", color = Color.Black, fontSize = 14.sp)
                        }
                    }
                }
            }

        }

        stickyHeader {
            Column(modifier = Modifier.background(Color.White)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    tabs2.forEachIndexed { index, title ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedTab = index }
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = title,
                                color = if (selectedTab == index) primary else third_03,
                                style = TebahTypography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                ) {
                    tabs2.forEachIndexed { index, _ ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .align(Alignment.Bottom)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(if (selectedTab == index) third_01 else third_03)
                                    .align(Alignment.BottomCenter)
                            )
                        }
                    }
                }
            }
        }

        when (selectedTab) {
            1 -> { // 채널 탭일 때만 2개 버튼
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { selectedSubTab01 = 0 },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedSubTab01 == 0) primary else Color.LightGray
                            )
                        ) {
                            Text("내 채널")
                        }
                        Button(
                            onClick = { selectedSubTab01 = 1 },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedSubTab01 == 1) primary else Color.LightGray
                            )
                        ) {
                            Text("참여 채널")
                        }
                    }
                }
            }

            2 -> {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        listOf("내 댓글", "좋아요", "북마크").forEachIndexed { index, label ->
                            Button(
                                onClick = { selectedSubTab02 = index },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedSubTab02 == index) primary else Color.LightGray
                                )
                            ) {
                                Text(label)
                            }
                        }
                    }
                }
            }
        }

        when (selectedTab) {
            0 -> { // 게시글
                items(posts) { post ->
                    PostPreviewCard(
                        isNotice = post.isNotice,
                        hasImages = post.hasImages,
                        profileImage = post.profileImage,
                        userId = post.userId,
                        postTime = post.postTime,
                        previewText = post.previewText,
                        imageList = post.imageList
                    )
                }
            }

            1 -> { // 채널
                when (selectedSubTab01) {
                    0 -> {
                        item {
                            MiniChannelItem()
                            MiniChannelItem()
                            MiniChannelItem()
                            MiniChannelItem()
                            MiniChannelItem()
                            MiniChannelItem()
                        }
                    }
                    1 -> {
                        item {
                            MiniChannelItem()
                            MiniChannelItem()
                            MiniChannelItem()
                        }
                    }
                }
            }

            2 -> { // 내 활동
                when (selectedSubTab02) {
                    0 -> item {
                        MiniCommentItem()
                        MiniCommentItem()
                        MiniCommentItem()
                        MiniCommentItem()
                        MiniCommentItem()
                    }
                    1 -> items(posts) { post ->
                        PostPreviewCard(
                            isNotice = post.isNotice,
                            hasImages = post.hasImages,
                            profileImage = post.profileImage,
                            userId = post.userId,
                            postTime = post.postTime,
                            previewText = post.previewText,
                            imageList = post.imageList
                        )
                    }
                    2 -> items(posts) { post ->
                        PostPreviewCard(
                            isNotice = post.isNotice,
                            hasImages = post.hasImages,
                            profileImage = post.profileImage,
                            userId = post.userId,
                            postTime = post.postTime,
                            previewText = post.previewText,
                            imageList = post.imageList
                        )
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    TebahTheme {
        ProfileScreen()
    }
}