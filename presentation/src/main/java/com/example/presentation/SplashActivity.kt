package com.example.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.presentation.login.LoginActivity
import com.example.presentation.main.admin.AdminMainActivity
import com.example.presentation.main.member.MemberMainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 여기서 API 호출 실패와 네트워크 문제 대비가 필요하다.

        lifecycleScope.launch {
            val isLoggedIn = false
            val isAdmin = false
            // isLoggedIn이 판별된 이후에 isAdmin을 조회하는 방식이 옳은 것 같다.
            // isAdmin을 서버에서 주기적으로 동기화시켜야할 필요성이 있음.
            // 기본적으로 로컬에 저장해둬도 괜찮음.

            if (isLoggedIn) {
                if (isAdmin) {
                    startActivity(Intent(this@SplashActivity, AdminMainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    })
                } else {
                    startActivity(Intent(this@SplashActivity, MemberMainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    })
                }
            } else {
                startActivity(
                    Intent(
                        this@SplashActivity, LoginActivity::class.java
                    ).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                )
            }
        }
    }
}