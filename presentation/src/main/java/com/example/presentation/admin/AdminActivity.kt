package com.example.presentation.admin

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.example.presentation.common.theme.TebahTheme
import com.example.presentation.admin.screen.AdminMainScreen
import com.example.presentation.admin.screen.ApprovalRequest
import com.example.presentation.admin.screen.ChurchInfo
import com.example.presentation.admin.screen.UnapprovedAdminScreen
import com.example.presentation.common.theme.Navy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = window
        val controller = WindowCompat.getInsetsController(window, window.decorView)
        window.navigationBarColor = Navy.toArgb()
        controller.isAppearanceLightNavigationBars = true

        setContent {
            TebahTheme {
                val isApproved = true // 서버에서 승인 상태를 가져와야 함

                if (isApproved) {
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
                    
                    AdminMainScreen(
                        churchInfo = churchInfo,
                        approvalRequests = approvalRequests
                    )
                } else {
                    UnapprovedAdminScreen()
                }
            }
        }
    }
}