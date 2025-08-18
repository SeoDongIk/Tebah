package com.example.presentation.member.screen.search

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.presentation.common.theme.Paddings
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchPromptScreen(
    onKeywordSelected: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        stickyHeader {

        }

        item {

        }
    }
}

@Composable
fun TebahSearchBar(
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
            // 🔙 뒤로가기 버튼
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint =  MaterialTheme.colorScheme.primary
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
                    .border(1.dp,  MaterialTheme.colorScheme.primary, RoundedCornerShape(Paddings.xlarge))
                    .background( MaterialTheme.colorScheme.primary.copy(0.1f))
                    .padding(horizontal = Paddings.xlarge)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint =  MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(Paddings.medium))

                Box(modifier = Modifier.weight(1f)) {
                    if (query.isEmpty()) {
                        Text(
                            text = placeholder,
                            color =  MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Start
                        )
                    }

                    BasicTextField(
                        value = query,
                        onValueChange = onQueryChange,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.titleMedium.copy(color =  MaterialTheme.colorScheme.primary),
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
                        cursorBrush = SolidColor( MaterialTheme.colorScheme.primary)
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
                            tint =  MaterialTheme.colorScheme.primary,
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