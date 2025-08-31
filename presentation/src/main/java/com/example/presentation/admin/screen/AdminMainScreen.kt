package com.example.presentation.admin.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.common.component.TebahTopBar
import com.example.presentation.common.theme.Paddings

// Data classes for preview purposes
data class ChurchInfo(
    val name: String,
    val creationDate: String,
    val adminName: String,
    val adminId: String,
    val phoneNumber: String,
    val address: String
)

data class ApprovalRequest(
    val id: String,
    val channelName: String,
    val channelManager: String,
    val creationDate: String
)

@Composable
fun AdminMainScreen(
    churchInfo: ChurchInfo,
    approvalRequests: List<ApprovalRequest>,
    onManageChannelsClick: () -> Unit = {},
    onManageMembersClick: () -> Unit = {},
    onManagePostsClick: () -> Unit = {},
    onApproveRequest: (String) -> Unit = {},
    onRejectRequest: (String) -> Unit = {},
    onUsageGuideClick: () -> Unit = {},
    onInquiryClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TebahTopBar()
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Paddings.medium, horizontal = Paddings.layout_horizontal),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onUsageGuideClick) {
                        Text("이용안내", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    TextButton(onClick = onInquiryClick) {
                        Text("1:1 문의", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
        ) {
            // 1. Church Information Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Paddings.layout_horizontal, vertical = Paddings.medium)
                    .shadow(6.dp, RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(Paddings.xlarge)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shadowElevation = 4.dp
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = churchInfo.name.take(2),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(Paddings.medium))
                        Column {
                            Text(
                                text = churchInfo.name,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "교회 채널 생성일: ${churchInfo.creationDate}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(Paddings.medium))
                    
                    // Church Details
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        ChurchDetailRow("관리자", churchInfo.adminName)
                        ChurchDetailRow("관리자 ID", churchInfo.adminId)
                        ChurchDetailRow("전화번호", churchInfo.phoneNumber)
                        ChurchDetailRow("주소", churchInfo.address)
                    }
                }
            }

            // 2. Quick Action Buttons Section
            Text(
                text = "빠른 실행",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = Paddings.layout_horizontal, vertical = Paddings.medium)
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Paddings.layout_horizontal),
                horizontalArrangement = Arrangement.spacedBy(Paddings.medium)
            ) {
                QuickActionCard(
                    title = "채널 관리",
                    icon = Icons.Default.Star,
                    onClick = onManageChannelsClick,
                    modifier = Modifier.weight(1f)
                )
                QuickActionCard(
                    title = "인원 관리",
                    icon = Icons.Default.Person,
                    onClick = onManageMembersClick,
                    modifier = Modifier.weight(1f)
                )
                QuickActionCard(
                    title = "게시글 관리",
                    icon = Icons.Default.List,
                    onClick = onManagePostsClick,
                    modifier = Modifier.weight(1f)
                )
            }

            // 3. Approval Requests Section
            Text(
                text = "승인 요청",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = Paddings.layout_horizontal, vertical = Paddings.medium)
            )

            // Approval Request Cards
            Column(
                modifier = Modifier.padding(horizontal = Paddings.layout_horizontal),
                verticalArrangement = Arrangement.spacedBy(Paddings.medium)
            ) {
                approvalRequests.forEach { request ->
                    ApprovalRequestCard(
                        request = request,
                        onApprove = { onApproveRequest(request.id) },
                        onReject = { onRejectRequest(request.id) }
                    )
                }
            }
            
            // Bottom spacing
            Spacer(modifier = Modifier.height(Paddings.xlarge))
        }
    }
}

@Composable
fun ChurchDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun QuickActionCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .shadow(3.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Paddings.large),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp)),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(Paddings.small))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun ApprovalRequestCard(
    request: ApprovalRequest,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(Paddings.xlarge)) {
            // Header with channel name
            Text(
                text = request.channelName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = Paddings.small)
            )
            
            // Channel details
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(bottom = Paddings.medium)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "채널장",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = request.channelManager,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "생성일",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = request.creationDate,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Paddings.small)
            ) {
                Button(
                    onClick = onApprove,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "승인",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("승인")
                }
                Button(
                    onClick = onReject,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "거절",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("거절")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminMainScreenPreview() {
    val churchInfo = ChurchInfo(
        name = "은혜광성교회",
        creationDate = "2025.05.06",
        adminName = "이환호 목사",
        adminId = "abcveh4567",
        phoneNumber = "02-1234-5678",
        address = "서울특별시 강동구 천중로 00길 00-0"
    )
    val approvalRequests = listOf(
        ApprovalRequest("1", "IT선교소모임", "편은정", "2025.04.04"),
        ApprovalRequest("2", "성경통독모임", "서율익", "2025.04.04")
    )
    AdminMainScreen(churchInfo = churchInfo, approvalRequests = approvalRequests)
}