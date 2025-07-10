package com.example.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.presentation.login.LoginActivity
import com.example.presentation.main.admin.AdminMainActivity
import com.example.presentation.main.member.MemberMainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    Timber.tag("SplashActivity").d("uiState collected: $state")
                    when (state) {
                        SplashUiState.Loading -> { /* 로딩 화면 유지 */ }
                        SplashUiState.GoToAdmin -> navigateTo(AdminMainActivity::class.java)
                        SplashUiState.GoToMember -> navigateTo(MemberMainActivity::class.java)
                        SplashUiState.GoToLogin -> navigateTo(LoginActivity::class.java)
                        SplashUiState.GoToStart -> showStartScreen()
                        SplashUiState.Error -> {
                            Toast.makeText(this@SplashActivity, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun navigateTo(targetActivity: Class<*>) {
        startActivity(Intent(this, targetActivity).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }

    private fun showStartScreen() {
        setContent {
            StartScreen(
                onNavigateToLogin = {
                    navigateTo(LoginActivity::class.java)
                },
                onBrowseServiceClick = {
                    navigateTo(MemberMainActivity::class.java)
                }
            )
        }
    }
}