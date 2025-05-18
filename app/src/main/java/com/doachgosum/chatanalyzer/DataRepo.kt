package com.doachgosum.chatanalyzer

import android.util.Log
import com.doachgosum.chatanalyzer.chat.ChatMessage
import com.doachgosum.chatanalyzer.chat.ChatMeta
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

object DataRepo {

    /**
     * key & text
     * */
    private val _cachedData: MutableStateFlow<MutableMap<String, ChatMeta>> = MutableStateFlow(mutableMapOf())

    init {
        GlobalScope.launch {
            _cachedData.collectLatest {
                Log.d("mytag", "cachedData >> $it")
            }
        }

    }

    fun saveChat(key: String, chat: ChatMeta) {
        _cachedData.update { current ->
            current.toMutableMap().apply {
                put(key, chat)
            }
        }
    }

    fun loadChat(key: String): ChatMeta? {
        return _cachedData.value[key]
    }

    fun loadAll() = _cachedData.value.map { it.value }

    fun observeAll() = _cachedData.asStateFlow()

}