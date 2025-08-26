package com.example.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.R
import com.example.presentation.auth.AuthActivity
import com.example.presentation.admin.AdminActivity
import com.example.presentation.member.MemberActivity
import com.example.presentation.splash.state.SplashUiState
import com.example.presentation.splash.state.SplashNavEvent
import com.example.presentation.common.theme.TebahTheme
import com.example.presentation.splash.screen.ErrorScreen
import com.example.presentation.splash.screen.LoadingScreen
import com.example.presentation.splash.screen.StartScreen
import com.example.presentation.splash.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TebahTheme {
                val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

                LaunchedEffect(Unit) {
                    viewModel.events.collect { event ->
                        when (event) {
                            SplashNavEvent.GoToAdmin -> navigateTo(AdminActivity::class.java)
                            SplashNavEvent.GoToMember -> navigateTo(MemberActivity::class.java)
                            SplashNavEvent.GoToPending -> navigateTo(MemberActivity::class.java) // placeholder
                            SplashNavEvent.GoToLogin -> navigateTo(AuthActivity::class.java)
                        }
                    }
                }

                when (uiState) {
                    SplashUiState.Loading -> LoadingScreen()
                    SplashUiState.Start -> {
                        StartScreen(
                            onNavigateToLogin = { navigateTo(AuthActivity::class.java) },
                            onBrowseServiceClick = {
                                viewModel.signInAsGuest(
                                    onSuccess = { navigateTo(MemberActivity::class.java) },
                                    onFailure = {
                                        Toast.makeText(
                                            this,
                                            getString(R.string.error_guest_login_failed),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                )
                            }
                        )
                    }
                    is SplashUiState.Error -> {
                        ErrorScreen(
                            message = stringResource(id = uiState.messageRes),
                            onRetry = { viewModel.retry() }
                        )
                    }
                }
            }
        }
    }

    private fun navigateTo(targetActivity: Class<*>) {
        Timber.tag(TAG).d("navigateTo: ${targetActivity.simpleName}")
        startActivity(Intent(this, targetActivity).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    companion object {
        private const val TAG = "SplashActivity"
    }
}
