package com.doachgosum.chatanalyzer.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.doachgosum.chatanalyzer.chat.ChatMessage
import com.doachgosum.chatanalyzer.chat.ChatMeta

@Composable
fun ChatDetailScreen(chatMeta: ChatMeta) {
    ChatDisplay(chatMeta)
}

@Composable
fun ChatDisplay(chatMeta: ChatMeta) {
    Column {
        Text(text = "채팅방: ${chatMeta.roomName}", style = MaterialTheme.typography.titleMedium)
        Text(text = "저장일: ${chatMeta.savedAt}", style = MaterialTheme.typography.bodySmall)
        Divider(
            modifier = Modifier
                .padding(vertical = 8.dp)
        )

        LazyColumn {
            items(chatMeta.messages) { msg ->
                ChatMessageItem(msg)
            }
        }
    }
}

@Composable
fun ChatMessageItem(msg: ChatMessage) {
    Column(
        modifier = Modifier
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "[${msg.datetime}] ${msg.sender}",
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = msg.message,
            style = MaterialTheme.typography.bodyMedium
        )
        Divider(
            modifier = Modifier
                .padding(vertical = 4.dp)
        )
    }
}