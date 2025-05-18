package com.doachgosum.chatanalyzer.chat

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertTrue
import org.junit.Test

class ChatParserTest {

    @Test
    fun parseSampleChatFile() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val inputStream = context.resources.openRawResource(R.raw.sample_chat)
        val chatText = inputStream.bufferedReader().use { it.readText() }

        val parsed = ChatParser.parseKakaoChat(chatText)

        // 전체 챗 데이터 출력
        println("채팅방 이름: ${parsed.roomName}")
        println("저장일: ${parsed.savedAt}")
        println("메시지 수: ${parsed.messages.size}")

        // 샘플 메시지 5개만 출력
        parsed.messages.take(5).forEachIndexed { index, msg ->
            println("[$index] ${msg.datetime} | ${msg.sender}: ${msg.message}")
        }

        // JSON 전체 출력
        val json = Json { prettyPrint = true }.encodeToString(parsed)
        println("\n전체 JSON:\n$json")

        assertTrue(parsed.roomName.isNotBlank())
        assertTrue(parsed.savedAt.isNotBlank())
        assertTrue(parsed.messages.isNotEmpty())
    }
}