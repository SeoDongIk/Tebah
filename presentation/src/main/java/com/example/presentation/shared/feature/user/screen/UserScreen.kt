package com.example.presentation.shared.feature.user.screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation.shared.component.MiniChannelItem
import com.example.presentation.shared.component.MiniCommentItem
import com.example.presentation.common.theme.TebahTypography
import com.example.presentation.common.theme.third_01
import com.example.presentation.common.theme.third_03
import com.example.presentation.shared.component.ChannelData
import com.example.presentation.shared.component.CommentData
import com.example.presentation.shared.component.PostData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserScreen(
    listState: LazyListState,
    onPostClick: (String) -> Unit,
    onUserClick: (String) -> Unit,
    onChannelClick: (String) -> Unit,
    onPhotoClick: (String) -> Unit,
    onEditProfile: () -> Unit,
) {
    var selectedTab by remember { mutableStateOf(0) }
    var channelSubTabIndex by remember { mutableStateOf(0) }
    var activitySubTabIndex by remember { mutableStateOf(0) }

    var showPhotoDialog by remember { mutableStateOf(false) }
    var showBioDialog by remember { mutableStateOf(false) }
    var editedBioText by remember { mutableStateOf("") }

    val profileImg = painterResource(R.drawable.profile_image)
    val sampleImg1 = painterResource(R.drawable.sample_image_01)
    val sampleImg2 = painterResource(R.drawable.sample_image_02)

    val posts = listOf(
        PostData("p1", true, true, profileImg, "관리자", "방금 전", "예배 안내", listOf(profileImg, sampleImg1), 23, 5, 18, true),
        PostData("p2", false, false, sampleImg1, "user123", "5분 전", "은혜로운 하루", emptyList(), 3, 1, 0, false)
    )
    val myChannels = listOf(ChannelData("1", "기도방", "함께 기도", 120, sampleImg1))
    val joinedChannels = listOf(ChannelData("2", "추천 채널", "다들 좋아함", 300, sampleImg2))
    val comments = listOf(CommentData("c1", "malcong", "좋은 글!", "1시간 전", profileImg))
    val likedPosts = posts.take(1)
    val savedPosts = posts

    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels / LocalContext.current.resources.displayMetrics.density
    val tabs = listOf(0, 1, 2)
    val offsets = tabs.map { tab ->
        animateDpAsState(
            targetValue = when (tab) {
                selectedTab -> 0.dp
                else -> if (tabs.indexOf(tab) < tabs.indexOf(selectedTab)) -screenWidth.dp else screenWidth.dp
            },
            animationSpec = tween(durationMillis = 250)
        )
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            UserProfileHeader(
                profileImage = profileImg,
                nickname = "MalcongMalcom",
                introduction = "안녕하세요! 개발자이자 예배자입니다.",
                followerCount = 4467,
                followingCount = 12,
                postCount = posts.size,
                onEditClick = { onEditProfile() },
                onProfileImageClick = { showPhotoDialog = true },
                onBioClick = {
                    showBioDialog = true
                    editedBioText = it
                }
            )
        }

        stickyHeader {
            Column(modifier = Modifier.background(Color.White)) {
                UserTopTabRow(
                    selectedTabIndex = selectedTab,
                    onTabSelected = { selectedTab = it },
                    textColor = Color.Black
                )

                when (selectedTab) {
                    1 -> SubTabRow(
                        tabs = listOf("내 채널", "참여 채널"),
                        selectedIndex = channelSubTabIndex,
                        onTabSelected = { channelSubTabIndex = it },
                        selectedColor = Color.Black,
                        unselectedColor = Color.Gray
                    )

                    2 -> SubTabRow(
                        tabs = listOf("내 댓글", "좋아요", "북마크"),
                        selectedIndex = activitySubTabIndex,
                        onTabSelected = { activitySubTabIndex = it },
                        selectedColor = Color.Black,
                        unselectedColor = Color.Gray
                    )
                }
            }
        }

        item {
            Box(modifier = Modifier.fillMaxWidth().heightIn(min = 600.dp)) {
                // 게시글
                Box(modifier = Modifier.offset(x = offsets[0].value)) {
                    Column {
                        posts.forEach { post ->
//                            PostPreviewCard2(
//                                data = post,
//                                onClick = { onPostClick(post.id) },
//                                onImageClick = { url -> onPhotoClick(url) },
//                                onUserClick = { onUserClick(post.userId) }
//                            )
                        }
                    }
                }

                // 채널
                Box(modifier = Modifier.offset(x = offsets[1].value)) {
                    Column {
                        if (channelSubTabIndex == 0) {
                            CreateChannelButton(onClick = { /* TODO */ })
                        }
                        val channels = if (channelSubTabIndex == 0) myChannels else joinedChannels
                        channels.forEach { channel ->
                            MiniChannelItem(channel, onChannelClick = { onChannelClick(channel.id) })
                        }
                    }
                }

                // 내 활동
                Box(modifier = Modifier.offset(x = offsets[2].value)) {
                    Column {
                        when (activitySubTabIndex) {
                            0 -> comments.forEach { comment ->
                                MiniCommentItem(comment, onCommentClick = { onPostClick(comment.id) })
                            }
                            1 -> likedPosts.forEach { post ->
//                                PostPreviewCard2(
//                                    data = post,
//                                    onClick = { onPostClick(post.id) },
//                                    onImageClick = { url -> onPhotoClick(url) },
//                                    onUserClick = { onUserClick(post.userId) }
//                                )
                            }
                            2 -> savedPosts.forEach { post ->
//                                PostPreviewCard2(
//                                    data = post,
//                                    onClick = { onPostClick(post.id) },
//                                    onImageClick = { url -> onPhotoClick(url) },
//                                    onUserClick = { onUserClick(post.userId) }
//                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserProfileHeader(
    profileImage: Painter,
    nickname: String,
    introduction: String,
    followerCount: Int,
    followingCount: Int,
    postCount: Int,
    onEditClick: () -> Unit,
    onProfileImageClick: () -> Unit,
    onBioClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = profileImage,
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .clickable { onProfileImageClick() },
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.clickable { onBioClick(introduction) }) {
                Text(nickname, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                Text(introduction, maxLines = 2, overflow = TextOverflow.Ellipsis, fontSize = 12.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.SpaceEvenly) {
                StatItem("팔로워", followerCount.toString())
                StatItem("팔로잉", followingCount.toString())
                StatItem("게시글", postCount.toString())
            }
            OutlinedButton(onClick = onEditClick, modifier = Modifier.height(36.dp), shape = RoundedCornerShape(20.dp)) {
                Text("프로필 편집", color = Color.Black)
            }
        }
    }
}

@Composable
fun SubTabRow(
    tabs: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    selectedColor: Color = Color.Black,
    unselectedColor: Color = Color.Gray
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEachIndexed { index, label ->
            Button(
                onClick = { onTabSelected(index) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedIndex == index) selectedColor else unselectedColor,
                    contentColor = Color.White
                )
            ) {
                Text(label)
            }
        }
    }
}

@Composable
fun UserTopTabRow(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    textColor: Color = Color.Black
) {
    val tabs = listOf("게시글", "채널", "내 활동")

    Column(modifier = Modifier.background(Color.White)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            tabs.forEachIndexed { index, title ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onTabSelected(index) }
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        color = if (selectedTabIndex == index) textColor else third_03,
                        style = TebahTypography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth().height(1.dp)) {
            tabs.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(if (selectedTabIndex == index) third_01 else third_03)
                )
            }
        }
    }
}

@Composable
fun CreateChannelButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEEEEEE))
    ) {
        Icon(Icons.Default.Add, contentDescription = "채널 만들기", tint = Color.Black)
        Spacer(modifier = Modifier.width(8.dp))
        Text("새 채널 만들기", color = Color.Black)
    }
}