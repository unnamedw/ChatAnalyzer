package com.doachgosum.chatanalyzer.ui.screen

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.doachgosum.chatanalyzer.DataRepo
import com.doachgosum.chatanalyzer.Screen
import com.doachgosum.chatanalyzer.chat.ChatMeta
import com.doachgosum.chatanalyzer.chat.ChatParser.parseKakaoChat

@Composable
fun TestScreen(
    rootNavController: NavController
) {
    val context = LocalContext.current

    val chatData by DataRepo.observeAll().collectAsStateWithLifecycle()
    Log.d("mytag", "chatData >> $chatData")

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val text = inputStream.bufferedReader().use { it.readText() }
                    val result = parseKakaoChat(text)
                    DataRepo.saveChat(result.roomName, result)
                }
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                launcher.launch(arrayOf("text/plain")) // MIME type
            }
        ) {
            Text("채팅 파일 불러오기")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            chatData.values.forEach { chat ->
                ChatRoomItem(
                    chatMeta = chat,
                    onItemClick = {
                        rootNavController.navigate(Screen.ChatDetail.route(chat.roomName)) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun ChatRoomItem(
    chatMeta: ChatMeta,
    onItemClick: (ChatMeta) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable {
                onItemClick.invoke(chatMeta)
            }
    ) {
        Text(text = "채팅방: ${chatMeta.roomName}", style = MaterialTheme.typography.titleMedium)
        Text(text = "저장일: ${chatMeta.savedAt}", style = MaterialTheme.typography.bodySmall)
        Divider(
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
    }
}