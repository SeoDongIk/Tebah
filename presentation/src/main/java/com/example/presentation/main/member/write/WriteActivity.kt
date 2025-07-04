package com.example.presentation.main.member.write

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.presentation.theme.TebahTheme
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