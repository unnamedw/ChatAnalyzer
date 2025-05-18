package com.doachgosum.chatanalyzer.chat

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object ChatParser {

    fun parseKakaoChat(text: String): ChatMeta {
        val lines = text.lines()

        val messages = mutableListOf<ChatMessage>()
        var roomName = ""
        var savedAt = ""

        val savedAtRegex = Regex("""저장한 날짜 *: *(.*)""")
        val titleRegex = Regex("""^(.+) 카카오톡 대화$""")
        val entryRegex = Regex("""(\d{4}년 \d{1,2}월 \d{1,2}일 (오전|오후) \d{1,2}:\d{2}), ([^:]+) : (.+)""")

        for (line in lines) {
            when {
                titleRegex.matches(line.trim()) -> {
                    roomName = titleRegex.find(line.trim())!!.groupValues[1]
                }

                savedAtRegex.matches(line.trim()) -> {
                    val savedRaw = savedAtRegex.find(line.trim())!!.groupValues[1]
                    savedAt = convertToIsoDateTime(savedRaw)
                }

                entryRegex.matches(line) -> {
                    val match = entryRegex.find(line)!!
                    val (datetimeStr, _, sender, message) = match.destructured
                    val isoDatetime = convertToIsoDateTime(datetimeStr)
                    messages.add(ChatMessage(isoDatetime, sender.trim(), message.trim()))
                }
            }
        }

        return ChatMeta(roomName = roomName, savedAt = savedAt, messages = messages)
    }

    private fun convertToIsoDateTime(kakaoDateTime: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 a h:mm")
            .withLocale(java.util.Locale.KOREAN)
        val outputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val parsed = LocalDateTime.parse(kakaoDateTime, formatter)
        return parsed.format(outputFormatter)
    }
}