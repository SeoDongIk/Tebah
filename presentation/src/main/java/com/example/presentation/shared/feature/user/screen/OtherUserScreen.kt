package com.example.presentation.shared.feature.user.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation.common.theme.TebahTheme

@Composable
fun OtherUserScreen(
    userProfile: UserProfileData,
    posts: List<PostData>,
    isFollowing: Boolean,
    onFollowToggle: () -> Unit,
    onUnfollowConfirm: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            Column(modifier = Modifier.background(Color.White)) {
                OtherUserProfileHeader(
                    profileImage = userProfile.profileImage,
                    nickname = userProfile.nickname,
                    introduction = userProfile.introduction,
                    followerCount = userProfile.followerCount,
                    followingCount = userProfile.followingCount,
                    postCount = userProfile.postCount,
                    isFollowing = isFollowing,
                    onFollowClick = {
                        if (isFollowing) {
                            showDialog = true
                        } else {
                            onFollowToggle()
                        }
                    }
                )
            }
        }

        if (posts.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("게시글이 없어요", color = Color.Gray, fontSize = 14.sp)
                }
            }
        } else {
            items(posts) { post ->
                PostPreviewCard(post)
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text("팔로우를 취소할까요?", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        onUnfollowConfirm()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("팔로우 취소", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("취소", color = Color.Black)
                }
            }
        )
    }
}

@Composable
fun OtherUserProfileHeader(
    profileImage: Painter,
    nickname: String,
    introduction: String,
    followerCount: Int,
    followingCount: Int,
    postCount: Int,
    isFollowing: Boolean,
    onFollowClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = profileImage,
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(nickname, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                Text(
                    text = introduction,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.SpaceEvenly) {
                StatItem("팔로워", followerCount.toString())
                StatItem("팔로잉", followingCount.toString())
                StatItem("게시글", postCount.toString())
            }

            Button(
                onClick = onFollowClick,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .height(36.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isFollowing) Color.White else Color.Black,
                    contentColor = if (isFollowing) Color.Black else Color.White
                ),
                border = if (isFollowing) BorderStroke(1.dp, Color.Gray) else null
            ) {
                Text(if (isFollowing) "팔로잉" else "팔로우")
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
        Text(text = label, fontSize = 12.sp, color = Color.Gray)
    }
}

@Preview(showBackground = true)
@Composable
fun OtherUserScreenPreview() {
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
    val profile = UserProfileData(
        profileImage = dummyImage,
        nickname = "animalllll_123",
        introduction = "독서를 좋아해요",
        followerCount = 4467,
        followingCount = 12,
        postCount = 135
    )
    TebahTheme {
        OtherUserScreen(
            userProfile = profile,
            posts = posts,
            isFollowing = true,
            onFollowToggle = {},
            onUnfollowConfirm = {}
        )
    }
}

data class UserProfileData(
    val profileImage: Painter,
    val nickname: String,
    val introduction: String,
    val followerCount: Int,
    val followingCount: Int,
    val postCount: Int
)
