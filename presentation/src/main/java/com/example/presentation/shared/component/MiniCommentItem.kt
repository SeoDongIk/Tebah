package com.example.presentation.shared.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.model.content.CircleShape
import com.example.presentation.R


@Composable
fun MiniCommentItem(comment: CommentData, onCommentClick: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(12.dp).clickable { onCommentClick(comment.id) }) {
        Image(
            painter = comment.profileImage ?: painterResource(R.drawable.sample_image_03),
            contentDescription = null,
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(comment.authorName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(comment.content, fontSize = 13.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(comment.timestamp, fontSize = 11.sp, color = Color.Gray)
        }
    }
}