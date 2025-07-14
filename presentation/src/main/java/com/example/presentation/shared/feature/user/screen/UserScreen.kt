package com.example.presentation.shared.feature.user.screen

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
import com.example.presentation.shared.component.MiniChannelItem
import com.example.presentation.shared.component.MiniCommentItem
import com.example.presentation.shared.component.PostPreviewCard2
import com.example.presentation.common.component.TebahTopBar
import com.example.presentation.common.component.TopBarIconData
import com.example.presentation.write.PostData2
import com.example.presentation.common.theme.Paddings
import com.example.presentation.common.theme.TebahTheme
import com.example.presentation.common.theme.TebahTypography
import com.example.presentation.common.theme.primary
import com.example.presentation.common.theme.third_01
import com.example.presentation.common.theme.third_03

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserScreen(pullProgress: Float) {
    var selectedTab by remember { mutableStateOf(0) }
    var selectedSubTab01 by remember { mutableStateOf(0) }
    var selectedSubTab02 by remember { mutableStateOf(0) }

    val dummyImage = painterResource(R.drawable.profile_image)
    val dummyImage2 = painterResource(R.drawable.sample_image_01)
    val dummyImage3 = painterResource(R.drawable.sample_image_02)

    val posts = listOf(
        // ✅ 공지글 + 이미지 + 좋아요/저장/체크
        PostData(
            id = "p1",
            isOfficial = true,
            hasImages = true,
            profileImage = dummyImage,
            userId = "관리자",
            postTime = "방금 전",
            previewText = "이번 주 예배 안내드립니다. 꼭 확인해주세요!",
            imageList = listOf(dummyImage, dummyImage2),
            likeCount = 23,
            saveCount = 5,
            checkCount = 18,
            isNotice = true
        ),
        // ✅ 일반글 + 이미지 없음
        PostData(
            id = "p2",
            isOfficial = false,
            hasImages = false,
            profileImage = dummyImage2,
            userId = "user123",
            postTime = "5분 전",
            previewText = "안녕하세요! 오늘도 은혜로운 하루 되세요 :)",
            likeCount = 3,
            saveCount = 1,
            checkCount = 0,
            isNotice = false
        ),
        // ✅ 일반글 + 이미지 여러 개
        PostData(
            id = "p3",
            isOfficial = false,
            hasImages = true,
            profileImage = dummyImage3,
            userId = "user456",
            postTime = "10분 전",
            previewText = "오늘 큐티 나눔이에요! 함께 은혜 나눠요 🙏",
            imageList = listOf(dummyImage2, dummyImage3),
            likeCount = 12,
            saveCount = 4,
            checkCount = 0,
            isNotice = false
        ),
        // ✅ 공식 채널 글이지만 공지는 아님
        PostData(
            id = "p4",
            isOfficial = true,
            hasImages = false,
            profileImage = dummyImage2,
            userId = "예배팀",
            postTime = "1시간 전",
            previewText = "이번 주 찬양곡 리스트를 공유드립니다.",
            likeCount = 30,
            saveCount = 10,
            checkCount = 0,
            isNotice = false
        ),
        // ✅ 공지글인데 이미지 없음
        PostData(
            id = "p5",
            isOfficial = false,
            hasImages = false,
            profileImage = dummyImage3,
            userId = "목사님",
            postTime = "2시간 전",
            previewText = "오늘 주보 첨부합니다. 모두 꼭 읽어주세요.",
            likeCount = 8,
            saveCount = 2,
            checkCount = 5,
            isNotice = true
        )
    )

    val myChannels = listOf(
        ChannelData("1", "기도방", "함께 기도해요", 120, dummyImage2),
        ChannelData("2", "QT 나눔", "오늘 말씀 나눠요", 85, dummyImage3)
    )

    val subscribedChannels = listOf(
        ChannelData("3", "추천 채널", "다들 좋아하는 채널이에요", 300, dummyImage)
    )

    val comments = listOf(
        CommentData("c1", "malcong", "좋은 글이네요!", "1시간 전", dummyImage),
        CommentData("c2", "guest", "감사합니다", "2시간 전", dummyImage2)
    )

    val likedPosts = posts.take(2)
    val savedPosts = posts

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            Column(modifier = Modifier.background(Color.White)) {
                // 로고
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.vector),
                        contentDescription = "로고 이미지",
                        tint = Color.Black, // 수정: primary → Color.Black
                        modifier = Modifier.size(48.dp + (pullProgress * 50).dp)
                    )
                }

                // 프로필 헤더
                UserProfileHeader(
                    profileImage = dummyImage,
                    nickname = "MalcongMalcom",
                    introduction = "안녕하세요! 개발자이자 예배자입니다.",
                    followerCount = 4467,
                    followingCount = 12,
                    postCount = posts.size,
                    onEditClick = { /* TODO */ }
                )
            }
        }

        // 탭 고정 영역
        stickyHeader {
            Column(modifier = Modifier.background(Color.White)) {
                UserTopTabRow(
                    selectedTabIndex = selectedTab,
                    onTabSelected = { selectedTab = it },
                    textColor = Color.Black // 명시적 전달 가능
                )

                when (selectedTab) {
                    1 -> SubTabRow(
                        tabs = listOf("내 채널", "참여 채널"),
                        selectedIndex = selectedSubTab01,
                        onTabSelected = { selectedSubTab01 = it },
                        selectedColor = Color.Black,
                        unselectedColor = Color.Gray
                    )

                    2 -> SubTabRow(
                        tabs = listOf("내 댓글", "좋아요", "북마크"),
                        selectedIndex = selectedSubTab02,
                        onTabSelected = { selectedSubTab02 = it },
                        selectedColor = Color.Black,
                        unselectedColor = Color.Gray
                    )
                }
            }
        }

        // 본문 콘텐츠
        when (selectedTab) {
            0 -> items(posts) { post ->
                PostPreviewCard(post)
            }

            1 -> {
                val channels = if (selectedSubTab01 == 0) myChannels else subscribedChannels
                if (selectedSubTab01 == 0) {
                    item {
                        CreateChannelButton(
                            onClick = { /* TODO */ },
                            textColor = Color.Black
                        )
                    }
                }
                items(channels) { channel ->
                    MiniChannelItem(channel, textColor = Color.Black)
                }
            }

            2 -> when (selectedSubTab02) {
                0 -> items(comments) { comment ->
                    MiniCommentItem(comment, textColor = Color.Black)
                }

                1 -> items(likedPosts) { post ->
                    PostPreviewCard(post)
                }

                2 -> items(savedPosts) { post ->
                    PostPreviewCard(post)
                }
            }
        }
    }
}