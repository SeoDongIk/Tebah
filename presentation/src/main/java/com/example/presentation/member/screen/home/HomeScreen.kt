 package com.example.presentation.member.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.presentation.R
import com.example.presentation.common.theme.TebahTypography
import com.example.presentation.common.theme.primary
import com.example.presentation.common.theme.third_01
import com.example.presentation.common.theme.third_03
import com.example.presentation.shared.component.PostData
import com.example.presentation.shared.component.PostPreviewCard2
import com.example.presentation.shared.feature.post.screen.PostPreviewCard

 @OptIn(ExperimentalFoundationApi::class)
 @Composable
 fun HomeScreen(
     listState: LazyListState,
     onPostClick: (String) -> Unit,
     onUserClick: (String) -> Unit,
     onChannelClick: (String) -> Unit,
 ) {
     var selectedTab by remember { mutableStateOf(0) }
     val tabs = listOf("팔로잉", "공지")

//     // listState 기반 pullProgress 계산 (0f ~ 1f)
//     val pullProgress by remember {
//         derivedStateOf {
//             val offset = listState.firstVisibleItemScrollOffset.coerceAtMost(100)
//             offset / 100f
//         }
//     }

     val dummyImage = painterResource(R.drawable.profile_image)
     val dummyImage2 = painterResource(R.drawable.sample_image_01)
     val dummyImage3 = painterResource(R.drawable.sample_image_02)

     val posts = listOf(
         PostData("p1", true, true, dummyImage, "관리자", "방금 전", "이번 주 예배 안내드립니다. 꼭 확인해주세요!", listOf(dummyImage, dummyImage2), 23, 5, 18, true),
         PostData("p2", false, false, dummyImage2, "user123", "5분 전", "안녕하세요! 오늘도 은혜로운 하루 되세요 :)", emptyList(), 3, 1, 0, false),
         PostData("p3", false, true, dummyImage3, "user456", "10분 전", "오늘 큐티 나눔이에요! 함께 은혜 나눠요 🙏", listOf(dummyImage2, dummyImage3), 12, 4, 0, false),
         PostData("p4", true, false, dummyImage2, "예배팀", "1시간 전", "이번 주 찬양곡 리스트를 공유드립니다.", emptyList(), 30, 10, 0, false),
         PostData("p6", false, false, dummyImage3, "목사님", "2시간 전", "오늘 주보 첨부합니다. 모두 꼭 읽어주세요.", emptyList(), 8, 2, 5, true),
         PostData("p7", false, false, dummyImage3, "목사님", "2시간 전", "오늘 주보 첨부합니다. 모두 꼭 읽어주세요.", emptyList(), 8, 2, 5, true),
         PostData("p8", false, false, dummyImage3, "목사님", "2시간 전", "오늘 주보 첨부합니다. 모두 꼭 읽어주세요.", emptyList(), 8, 2, 5, true),
         PostData("p9", false, false, dummyImage3, "목사님", "2시간 전", "오늘 주보 첨부합니다. 모두 꼭 읽어주세요.", emptyList(), 8, 2, 5, true),
         )

     LazyColumn(
         state = listState,
         modifier = Modifier
             .fillMaxSize()
             .background(Color.White)
     ) {
         // ✅ pullProgress 기반 로고 크기 & 위치 변화
//         item {
//             Box(
//                 modifier = Modifier
//                     .fillMaxWidth()
//                     .height(100.dp),
//                 contentAlignment = Alignment.Center
//             ) {
//                 Icon(
//                     painter = painterResource(id = R.drawable.vector),
//                     contentDescription = "로고 이미지",
//                     tint = primary,
//                     modifier = Modifier
//                         .size(48.dp + (pullProgress * 50).dp)
//                         .offset(y = (pullProgress * 24).dp)
//                 )
//             }
//         }

         // ✅ 탭 헤더 고정
         stickyHeader {
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

         // ✅ 게시물 리스트
         items(posts) { post ->
         }
     }
 }
