package com.doachgosum.chatanalyzer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.doachgosum.chatanalyzer.chat.ChatParser
import com.doachgosum.chatanalyzer.ui.screen.RootScreen
import com.doachgosum.chatanalyzer.ui.theme.ChatAnalyzerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleIntent(intent)

        enableEdgeToEdge()
        setContent {
            ChatAnalyzerTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    RootScreen(innerPadding)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val sharedTextUri = intent
            .takeIf { it.action == Intent.ACTION_SEND }
            ?.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)

        saveTextFromUri(applicationContext, sharedTextUri)
    }
}

private fun saveTextFromUri(context: Context, uri: Uri?) {
    uri?.let {
        val text = context.contentResolver.openInputStream(it)?.bufferedReader()?.use { br -> br.readText() }
        text?.let {
            runCatching {
                ChatParser.parseKakaoChat(it)
            }.onSuccess { chat ->
                DataRepo.saveChat(chat.roomName, chat)
            }.onFailure { e ->
                Log.e("Parser", "파싱 실패: ${e.message}")
            }
        }
    }
}