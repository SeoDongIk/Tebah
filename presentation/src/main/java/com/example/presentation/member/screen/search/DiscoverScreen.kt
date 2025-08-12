package com.example.presentation.member.screen.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.presentation.R
import com.example.presentation.common.theme.Paddings
import com.example.presentation.common.theme.TebahTypography
import com.example.presentation.common.theme.primary
import com.example.presentation.common.theme.third_01
import com.example.presentation.common.theme.third_03
import com.example.presentation.member.screen.MemberNavigator


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiscoverScreen(
    listState: LazyListState,
    navigator: MemberNavigator,
    onNavigateToSearchDetail: () -> Unit
) {

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        stickyHeader {
            TebahSearchBarMock(
                onNavigateToSearchDetail = onNavigateToSearchDetail
            )
        }

    }
}

@Composable
fun TebahSearchBarMock(
    modifier: Modifier = Modifier,
    placeholder: String = "검색",
    onNavigateToSearchDetail: () -> Unit
) {
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
                    style = MaterialTheme.typography.titleMedium,
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