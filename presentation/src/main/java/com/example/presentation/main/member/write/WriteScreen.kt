package com.example.presentation.main.member.write

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.presentation.R
import com.example.presentation.component.MediumButton
import com.example.presentation.component.PostActions
import com.example.presentation.component.PostHeader
import com.example.presentation.component.PostImages
import com.example.presentation.component.PostPreviewCard
import com.example.presentation.component.PostWriteCard
import com.example.presentation.component.TebahTopBar
import com.example.presentation.component.TopBarIconData
import com.example.presentation.main.member.search.TebahSearchBarMock
import com.example.presentation.model.PostData
import com.example.presentation.theme.Paddings
import com.example.presentation.theme.TebahTheme
import com.example.presentation.theme.TebahTypography
import com.example.presentation.theme.primary
import com.example.presentation.theme.secondary
import com.example.presentation.theme.third_01
import com.example.presentation.theme.third_02
import com.example.presentation.theme.third_03
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WriteScreen(
) {
    val dummyImage = painterResource(R.drawable.profile_image)

    val post = PostData(
        isNotice = false,
        hasImages = false,
        profileImage = dummyImage,
        userId = "user_02",
        postTime = "1시간 전",
        previewText = "이미지는 없지만 내용은 충실한 게시글입니다.공지사항입는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입" +
                "는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입" +
                "는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입" +
                "는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입" +
                "는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입" +
                "는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입" +
                "는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입" +
                "는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입" +
                "는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입" +
                "는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입" +
                "는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입" +
                "는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입" +
                "는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입" +
                "는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입" +
                "는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입" +
                "는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입" +
                "는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입" +
                "는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입" +
                "는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입는 없지만 내용은 충실한 게시글입니다.공지사항입니다. 필수 확인 바랍니다.공지사항입" +
                "" +
                "" +
                "니다. 필수 확인 바랍니다.공지사항입니다. 필수 확인 바랍니다."
    )
    var showDialog by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    var previewText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(bottom = 64.dp)
                .fillMaxSize()
                .imePadding() // Column도 밀려야 하니까 유지
        ) {
            TebahTopBar(
                title = "새로운 글쓰기_공지글",
                iconsRight = listOf(
                    TopBarIconData(
                        icon = Icons.Default.Close,
                        contentDescription = "닫기",
                        onClick = { /* TODO: 닫기 동작 */ }
                    )
                ),
                dividerVisible = false
            )
            LazyColumn(
                modifier = Modifier
                    .background(Color.White)
                    .wrapContentHeight()
                    .weight(1f, fill = false),
                reverseLayout = true
            ) {
                item {
                    PostWriteCard(
                        isNotice = post.isNotice,
                        hasImages = post.hasImages,
                        profileImage = post.profileImage,
                        userId = post.userId,
                        postTime = post.postTime,
                        previewText = previewText,
                        imageList = post.imageList,
                        onValueChange = {
                            previewText = it
                        },
                        focusRequester = focusRequester
                    )
                }
            }
        }

        // ✅ 버튼 박스에 imePadding을 주거나 navigationBarsWithImePadding 사용 -> 미구현 ㅜㅜ
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(third_03.copy(0.1f))
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 왼쪽 아이콘들
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "카메라",
                        tint = third_01,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "갤러리",
                        tint = third_01,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
                // 오른쪽 게시 버튼
                Button(
                    onClick = { /* TODO: 게시 클릭 */ },
                    colors = ButtonDefaults.buttonColors(containerColor = third_01.copy(1f)),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                    modifier = Modifier.padding(Paddings.small)
                ) {
                    Text(
                        text = "게시",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("공지 등록 안내") },
            text = { Text("공지로 등록된 게시글은 상단에 고정됩니다.") },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("확인")
                }
            }
        )
    }
}

@Composable
fun TebahSearchBarMock2(
    modifier: Modifier = Modifier,
    placeholder: String = "검색",
    onNavigateToSearchDetail: () -> Unit
) {

    //            TebahSearchBarMock2(
//                onNavigateToSearchDetail = {}
//            )
//            Row(
//                modifier = Modifier
//                    .horizontalScroll(scrollState)
//                    .background(Color.White)
//                    .padding(start = Paddings.medium, end = Paddings.medium, bottom = Paddings.medium, top = Paddings.medium),
//            ) {
//                regions.forEach { region ->
//                    Row(
//                        modifier = Modifier
//                            .wrapContentSize()
//                            .border(
//                                width = 1.dp,
//                                color = third_03.copy(0.4f),
//                                shape = RoundedCornerShape(12.dp)
//                            )
//                            .clip(RoundedCornerShape(12.dp))
//                            .background(Color.White) // 배경색 넣고 싶다면 여기에
//                            .clickable { /* TODO: 전체 클릭 동작 */ }
//                            .padding(horizontal = 12.dp, vertical = 6.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = region,
//                            fontSize = 13.sp,
//                            color = Color.Black
//                        )
//
//                        Spacer(modifier = Modifier.width(6.dp))
//
//                        Icon(
//                            imageVector = Icons.Default.Close,
//                            contentDescription = "삭제",
//                            modifier = Modifier
//                                .size(18.dp)
//                                .clickable { /* TODO: X 클릭 동작 */ },
//                            tint = Color.Black
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(6.dp))
//                }
//            }
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

@Composable
fun ChannelAddCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(width = 100.dp, height = 120.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Channel",
                modifier = Modifier.size(32.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "채널 추가",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ChannelCard(
    channelName: String,
    channelDesc: String,
    profileImage: Painter
) {
    Card(
        modifier = Modifier
            .size(width = 160.dp, height = 120.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = profileImage,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = channelName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
            Text(
                text = channelDesc,
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ChannelScrollRow(
    channels: List<Pair<String, String>>, // Pair<이름, 설명>
    profileImage: Painter, // 임시로 하나만
    onAddClick: () -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            ChannelAddCard(onClick = onAddClick)
        }

        items(channels) { (name, desc) ->
            ChannelCard(
                channelName = name,
                channelDesc = desc,
                profileImage = profileImage
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun WriteScreenPreview() {
//    TebahTheme {
//        WriteScreen()
//    }
//}