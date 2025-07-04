package com.example.presentation.main.member.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.R
import com.example.presentation.component.MiniChannelItem
import com.example.presentation.component.MiniCommentItem
import com.example.presentation.component.MiniProfileItem
import com.example.presentation.component.PostPreviewCard
import com.example.presentation.model.PostData
import com.example.presentation.theme.Paddings
import com.example.presentation.theme.TebahTypography
import com.example.presentation.theme.primary
import com.example.presentation.theme.third_01
import com.example.presentation.theme.third_02
import com.example.presentation.theme.third_03

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewSearchScreenV3(
    query: String,
    onBackClick: () -> Unit
) {
    val isQueryNotEmpty = query.isNotBlank()

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("추천", "계정", "채널", "게시글")

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

        stickyHeader {
            Column(modifier = Modifier.background(Color.White)) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Paddings.medium)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = Paddings.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 🔙 뒤로가기 버튼
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = primary
                        )
                    }

                    Spacer(modifier = Modifier.width(Paddings.medium))

                    // 🔍 검색바 영역
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(Paddings.xlarge))
                            .border(1.dp, third_01, RoundedCornerShape(Paddings.xlarge))
                            .background(Color.White)
                            .padding(horizontal = Paddings.xlarge)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = primary
                        )

                        Spacer(modifier = Modifier.width(Paddings.medium))

                        Box(modifier = Modifier.weight(1f)) {
                            if (!isQueryNotEmpty) {
                                Text(
                                    text = "",
                                    color = third_03,
                                    style = TebahTypography.titleMedium,
                                    textAlign = TextAlign.Start
                                )
                            } else {
                                Text(
                                    text = query!!,
                                    style = TebahTypography.titleMedium.copy(color = primary),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                        }

                        IconButton(
                            onClick = onBackClick,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear",
                                tint = third_03,
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Paddings.medium)
                )
            }
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
                                    .background(if (selectedTab == index) third_01 else third_03)
                                    .align(Alignment.BottomCenter)
                            )
                        }
                    }
                }
            }
        }

        when(selectedTab) {
            0 -> {
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
            1 -> {
                item {
                    MiniProfileItem()
                    MiniProfileItem()
                    MiniProfileItem()
                    MiniProfileItem()
                    MiniProfileItem()
                    MiniProfileItem()
                    MiniProfileItem()
                    MiniProfileItem()
                    MiniProfileItem()
                    MiniProfileItem()
                    MiniProfileItem()
                    MiniProfileItem()
                    MiniProfileItem()
                    MiniProfileItem()
                    MiniProfileItem()
                    MiniProfileItem()
                    MiniProfileItem()
                    MiniProfileItem()
                }
            }
            2 -> {
                item {
                    MiniChannelItem()
                    MiniChannelItem()
                    MiniChannelItem()
                    MiniChannelItem()
                    MiniChannelItem()
                    MiniChannelItem()
                    MiniChannelItem()
                    MiniChannelItem()
                    MiniChannelItem()
                    MiniChannelItem()
                    MiniChannelItem()
                }
            }
            3 -> {
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
        }


    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewNewSearchScreenV3() {
    NewSearchScreenV3(
        query = "검색어 예시",
        onBackClick = {}
    )
}