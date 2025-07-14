package com.example.presentation.shared.feature.user.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation.common.theme.TebahTypography
import com.example.presentation.common.theme.third_01
import com.example.presentation.common.theme.third_03

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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        ) {
            tabs.forEachIndexed { index, _ ->
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
                            .background(if (selectedTabIndex == index) third_01 else third_03)
                            .align(Alignment.BottomCenter)
                    )
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
    onEditClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White) // 🔸 배경 명시적으로 지정
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                contentScale = ContentScale.Crop, // 🔥 핵심!
                painter = profileImage,
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = nickname,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black // ✅ 명시적 색상
                )
                Text(
                    text = introduction,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp,
                    color = Color.Gray // ✅ 약간 옅게
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem("팔로워", followerCount.toString(), textColor = Color.Black)
                StatItem("팔로잉", followingCount.toString(), textColor = Color.Black)
                StatItem("게시글", postCount.toString(), textColor = Color.Black)
            }
            OutlinedButton(
                onClick = onEditClick,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .height(36.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("프로필 편집", color = Color.Black) // ✅ 버튼 텍스트도 검정색
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String, textColor: Color = Color.Black) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold, color = textColor)
        Text(label, fontSize = 12.sp, color = textColor.copy(alpha = 0.7f))
    }
}

@Composable
fun CreateChannelButton(
    onClick: () -> Unit,
    textColor: Color = Color.Black
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEEEEEE))
    ) {
        Icon(Icons.Default.Add, contentDescription = "채널 만들기", tint = textColor)
        Spacer(modifier = Modifier.width(8.dp))
        Text("새 채널 만들기", color = textColor)
    }
}

data class ChannelData(
    val id: String,
    val name: String,
    val description: String,
    val subscriberCount: Int,
    val thumbnail: Painter? = null // 썸네일이 없을 수도 있음
)

@Composable
fun MiniChannelItem(
    channel: ChannelData,
    textColor: Color = Color.Black
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(Color.White)
    ) {
        Image(
            painter = channel.thumbnail ?: painterResource(R.drawable.sample_image_01),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(channel.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = textColor)
            Text(
                text = channel.description,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = textColor
            )
            Text(
                text = "${channel.subscriberCount}명 구독 중",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

data class CommentData(
    val id: String,
    val authorName: String,
    val content: String,
    val timestamp: String,
    val profileImage: Painter? = null
)

@Composable
fun MiniCommentItem(
    comment: CommentData,
    textColor: Color = Color.Black
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Image(
            painter = comment.profileImage ?: painterResource(R.drawable.sample_image_03),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(comment.authorName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = textColor)
            Text(comment.content, fontSize = 13.sp, maxLines = 2, overflow = TextOverflow.Ellipsis, color = textColor)
            Text(comment.timestamp, fontSize = 11.sp, color = Color.Gray)
        }
    }
}

@Composable
fun PostPreviewCard(post: PostData) {
    val isOfficial = post.isOfficial
    val isNotice = post.isNotice
    val officialColor = Color(0xFF1565C0) // 세련된 딥블루

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        // 🔷 공식 채널 스트립
        if (isOfficial) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .background(officialColor)
            )
        }

        Column(modifier = Modifier.padding(12.dp)) {
            // 👤 작성자 정보
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    contentScale = ContentScale.Crop, // 🔥 핵심!
                    painter = post.profileImage,
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = post.userId,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                        if (isOfficial) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "공식 채널",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier
                                    .background(officialColor, shape = RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = post.postTime,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 📝 본문
            Text(
                text = post.previewText,
                fontSize = 14.sp,
                color = Color.Black,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 20.sp
            )

            // 🖼️ 이미지
            if (post.hasImages && post.imageList.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(post.imageList) { image ->
                        Image(
                            painter = image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray)
                        )
                    }
                }
            }

            // ❤️ 📌 ✔️ 액션 버튼
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconToggleWithText(
                    icon = Icons.Default.FavoriteBorder,
                    toggledIcon = Icons.Default.Favorite,
                    isSelectedInitial = false,
                    countInitial = post.likeCount,
                    selectedColor = Color.Red
                )
                IconToggleWithText(
                    icon = Icons.Default.FavoriteBorder,
                    toggledIcon = Icons.Default.Favorite,
                    isSelectedInitial = false,
                    countInitial = post.saveCount,
                    selectedColor = Color(0xFF1976D2) // 파란색
                )
                if (post.isNotice) {
                    IconToggleWithText(
                        icon = Icons.Default.FavoriteBorder,
                        toggledIcon = Icons.Default.Favorite,
                        isSelectedInitial = false,
                        countInitial = post.checkCount,
                        selectedColor = Color(0xFF2E7D32) // 녹색
                    )
                }
            }
        }
    }
}

@Composable
fun IconToggleWithText(
    icon: ImageVector,
    toggledIcon: ImageVector = icon, // 필요 시 변경 가능
    isSelectedInitial: Boolean,
    countInitial: Int,
    selectedColor: Color,
) {
    var isSelected by remember { mutableStateOf(isSelectedInitial) }
    var count by remember { mutableStateOf(countInitial) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            isSelected = !isSelected
            count += if (isSelected) 1 else -1
        }
    ) {
        Icon(
            imageVector = if (isSelected) toggledIcon else icon,
            contentDescription = null,
            tint = if (isSelected) selectedColor else Color.Gray,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = count.toString(),
            fontSize = 12.sp,
            color = if (isSelected) selectedColor else Color.Gray
        )
    }
}

data class PostData(
    val id: String = "",
    val isOfficial: Boolean = false,
    val hasImages: Boolean = false,
    val profileImage: Painter,
    val userId: String,
    val postTime: String,
    val previewText: String,
    val imageList: List<Painter> = emptyList(),

    // 좋아요/저장/체크 관련
    val likeCount: Int = 0,
    val saveCount: Int = 0,
    val checkCount: Int = 0,
    val isNotice: Boolean = false // 공지 기준 (체크 활성화 용도)
)