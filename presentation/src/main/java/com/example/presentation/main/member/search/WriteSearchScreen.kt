package com.example.presentation.main.member.search

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.component.LargeButton
import com.example.presentation.component.MiniChannelItem
import com.example.presentation.component.TebahTopBar
import com.example.presentation.component.TopBarIconData
import com.example.presentation.theme.Paddings
import com.example.presentation.theme.TebahTypography
import com.example.presentation.theme.primary
import com.example.presentation.theme.third_01
import com.example.presentation.theme.third_03
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WriteSearchScreen(
    onBackClick: () -> Unit,
    onSearch: (Any?) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            stickyHeader {
                TebahTopBar(
                    title = "채널 검색",
                    iconsRight = listOf(
                        TopBarIconData(
                            icon = Icons.Filled.Close,
                            contentDescription = "닫기",
                            onClick = { }
                        )
                    ),
                    dividerVisible = false
                )
                TebahSearchBar2(
                    query = query,
                    onQueryChange = { query = it },
                    focusRequester = focusRequester,
                    onBackClick = onBackClick,
                    onSearch = onSearch
                )
            }

            item {
                Box {
                    MiniChannelItem()
                    Checkbox(
                        checked = false,
                        onCheckedChange = {  },
                        modifier = Modifier.size(90.dp)
                            .align(Alignment.CenterEnd)
                            .scale(1.5f)
                    )
                }
                Box {
                    MiniChannelItem()
                    Checkbox(
                        checked = false,
                        onCheckedChange = {  },
                        modifier = Modifier.size(90.dp)
                            .align(Alignment.CenterEnd)
                            .scale(1.5f)
                    )
                }
                Box {
                    MiniChannelItem()
                    Checkbox(
                        checked = false,
                        onCheckedChange = {  },
                        modifier = Modifier.size(90.dp)
                            .align(Alignment.CenterEnd)
                            .scale(1.5f)
                    )
                }
                Box {
                    MiniChannelItem()
                    Checkbox(
                        checked = false,
                        onCheckedChange = {  },
                        modifier = Modifier.size(90.dp)
                            .align(Alignment.CenterEnd)
                            .scale(1.5f)
                    )
                }
                Box {
                    MiniChannelItem()
                    Checkbox(
                        checked = false,
                        onCheckedChange = {  },
                        modifier = Modifier.size(90.dp)
                            .align(Alignment.CenterEnd)
                            .scale(1.5f)
                    )
                }
                Box {
                    MiniChannelItem()
                    Checkbox(
                        checked = false,
                        onCheckedChange = {  },
                        modifier = Modifier.size(90.dp)
                            .align(Alignment.CenterEnd)
                            .scale(1.5f)
                    )
                }
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp))
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter)
                .background(Color.White)
        ) {
            LargeButton(
                text = "완료",
                modifier = Modifier.padding(Paddings.medium),
                onClick =  {  },
                backgroundColor = primary
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(
    name = "WriteSearchScreen",
    showBackground = true,
    backgroundColor = 0xFFFFFF
)
@Composable
fun WriteSearchScreenPreview() {
    WriteSearchScreen(
        onBackClick = {},
        onSearch = {}
    )
}

@Composable
fun TebahSearchBar2(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "검색",
    onClearClick: (() -> Unit)? = null,
    onBackClick: () -> Unit,
    focusRequester: FocusRequester,
    onSearch: (Any?) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val isQueryNotEmpty = query.isNotBlank()

    LaunchedEffect(Unit) {
        delay(100) // 딜레이는 필요하면 유지
        focusRequester.requestFocus()
    }

    Column(modifier = modifier.background(Color.White)) {
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
            // 🔍 검색바 영역
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(Paddings.xlarge))
                    .border(1.dp, third_01, RoundedCornerShape(Paddings.xlarge))
                    .background(third_03.copy(0.1f))
                    .padding(horizontal = Paddings.xlarge)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = primary
                )

                Spacer(modifier = Modifier.width(Paddings.medium))

                Box(modifier = Modifier.weight(1f)) {
                    if (query.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = third_03,
                            style = TebahTypography.titleMedium,
                            textAlign = TextAlign.Start
                        )
                    }

                    BasicTextField(
                        value = query,
                        onValueChange = onQueryChange,
                        singleLine = true,
                        textStyle = TebahTypography.titleMedium.copy(color = primary),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                onSearch(query)
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        cursorBrush = SolidColor(third_03)
                    )
                }

                if (isQueryNotEmpty) {
                    IconButton(
                        onClick = {
                            onQueryChange("")
                            onClearClick?.invoke()
                            focusManager.clearFocus()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear",
                            tint = third_03,
                        )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(Paddings.medium)
        )
    }
}