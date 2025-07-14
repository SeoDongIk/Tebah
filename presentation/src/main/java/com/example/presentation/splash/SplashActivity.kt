package com.example.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.presentation.R
import com.example.presentation.auth.AuthActivity
import com.example.presentation.admin.AdminActivity
import com.example.presentation.member.MemberActivity
import com.example.presentation.splash.state.SplashUiState
import com.example.presentation.common.theme.TebahTheme
import com.example.presentation.splash.screen.StartScreen
import com.example.presentation.splash.viewmodel.SplashViewModel
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
                        SplashUiState.GoToAdmin -> navigateTo(AdminActivity::class.java)
                        SplashUiState.GoToMember -> navigateTo(MemberActivity::class.java)
                        SplashUiState.GoToLogin -> navigateTo(AuthActivity::class.java)
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
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    private fun showStartScreen() {
        setContent {
            TebahTheme {
                StartScreen(
                    onNavigateToLogin = {
                        navigateTo(AuthActivity::class.java)
                    },
                    onBrowseServiceClick = {
                        navigateTo(MemberActivity::class.java)
                    }
                )
            }
        }
    }
}