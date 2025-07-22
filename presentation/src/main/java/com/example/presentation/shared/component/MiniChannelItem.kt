package com.example.presentation.shared.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R

@Composable
fun MiniChannelItem(channel: ChannelData, onChannelClick: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(12.dp).clickable { onChannelClick(channel.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = channel.thumbnail ?: painterResource(R.drawable.sample_image_01),
            contentDescription = null,
            modifier = Modifier.size(48.dp).clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(channel.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(channel.description, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text("${channel.subscriberCount}명 구독 중", fontSize = 12.sp, color = Color.Gray)
        }
    }
}
