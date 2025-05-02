package com.example.presentation.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.domain.model.ACTION_POSTED
import com.example.presentation.theme.TebahTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(

) {

    // TODO: boardViewModel 주입하기
    private val receiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent?.action == ACTION_POSTED) {
                // TODO: load()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TebahTheme {
                MainScreen()
            }
        }

        ContextCompat.registerReceiver(
            this,
            receiver,
            IntentFilter(ACTION_POSTED),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }
}