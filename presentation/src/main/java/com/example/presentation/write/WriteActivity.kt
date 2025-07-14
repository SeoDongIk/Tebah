package com.example.presentation.write

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.presentation.common.theme.TebahTheme
import com.example.presentation.write.screen.WriteScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TebahTheme {
                WriteScreen()
            }
        }
    }
}