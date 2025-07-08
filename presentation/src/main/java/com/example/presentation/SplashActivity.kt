package com.example.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.presentation.login.LoginActivity
import com.example.presentation.login.LoginSideEffect
import com.example.presentation.main.admin.AdminMainActivity
import com.example.presentation.main.member.MemberMainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        SplashUiState.Loading -> { }
                        SplashUiState.GoToAdmin -> { startMainActivity(AdminMainActivity::class.java) }
                        SplashUiState.GoToMember -> { startMainActivity(MemberMainActivity::class.java) }
                        SplashUiState.GoToLogin -> { startMainActivity(LoginActivity::class.java) }
                        SplashUiState.Error -> { }
                    }
                }
            }
        }
    }

    private fun startMainActivity(target: Class<*>) {
        startActivity(Intent(this, target).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
        finish()
    }
}