package com.doachgosum.chatanalyzer.chat

import kotlinx.serialization.Serializable

@Serializable
data class ChatMeta(
    val roomName: String,
    val savedAt: String,
    val messages: List<ChatMessage>
)

@Serializable
data class ChatMessage(
    val datetime: String,
    val sender: String,
    val message: String
)