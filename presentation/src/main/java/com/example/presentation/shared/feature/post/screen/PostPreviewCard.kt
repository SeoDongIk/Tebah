package com.example.presentation.shared.feature.post.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.shared.component.PostData

@Composable
fun PostPreviewCard(
    data: PostData,
    onClick: () -> Unit,
    onImageClick: (String) -> Unit,
    onUserClick: () -> Unit
) {
    val officialColor = Color(0xFF1565C0)
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clip(RoundedCornerShape(12.dp)).background(Color.White)
            .clickable { onClick() }
    ) {
        if (data.isOfficial) {
            Box(modifier = Modifier.fillMaxWidth().height(4.dp).background(officialColor))
        }
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = data.profileImage,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp).clip(CircleShape).clickable { onUserClick() },
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(data.userId, fontWeight = FontWeight.SemiBold)
                    Text(data.postTime, fontSize = 12.sp, color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(data.previewText, maxLines = 5, overflow = TextOverflow.Ellipsis)
            if (data.hasImages) {
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(data.imageList) { img ->
                        Image(
                            painter = img,
                            contentDescription = null,
                            modifier = Modifier.size(120.dp).clip(RoundedCornerShape(8.dp)).clickable { onImageClick("imageUrl") },
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}