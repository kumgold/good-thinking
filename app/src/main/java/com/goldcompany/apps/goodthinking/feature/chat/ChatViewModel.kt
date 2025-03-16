package com.goldcompany.apps.goodthinking.feature.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldcompany.apps.goodthinking.data.db.chat.toDefault
import com.goldcompany.apps.goodthinking.data.repo.DefaultRepository
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.asTextOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val generativeModel: GenerativeModel,
    private val repository: DefaultRepository
) : ViewModel() {

    private lateinit var chat: Chat

    private val _uiState: MutableStateFlow<ChatUiState> = MutableStateFlow(ChatUiState(emptyList()))
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        init()
    }

    private fun init() {
        viewModelScope.launch {
            val messages = repository.getAllMessages().map { it.toDefault() }

            initializeChatMessages(messages)
        }
    }

    private fun initializeChatMessages(messages: List<ChatMessage>) {
        val history = messages.map { message -> message.toContent() }

        chat = generativeModel.startChat(history = history)

        _uiState.value = ChatUiState(chat.history.mapIndexed { index, content ->
            ChatMessage(
                id = messages[index].id,
                text = content.parts.first().asTextOrNull() ?: "",
                participant = if (content.role == "user") Participant.USER else Participant.MODEL,
                isPending = false
            )
        })
    }

    fun sendMessage(userMessage: String) {
        viewModelScope.launch {
            try {
                addMessage(
                    ChatMessage(
                        text = userMessage,
                        participant = Participant.USER,
                        isPending = true
                    )
                )

                val response = chat.sendMessage(userMessage)

                _uiState.value.replaceLastPendingMessage()

                response.text?.let { modelResponse ->
                    addMessage(
                        ChatMessage(
                            text = modelResponse,
                            participant = Participant.MODEL,
                            isPending = false
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.value.replaceLastPendingMessage()
                _uiState.value.addMessage(
                    ChatMessage(
                        text = e.localizedMessage ?: "",
                        participant = Participant.ERROR
                    )
                )
            }
        }
    }

    private suspend fun addMessage(message: ChatMessage) {
        repository.insertMessage(message.toLocal())
        _uiState.value.addMessage(message)
    }

    private val ids = mutableListOf<String>()

    fun selectMessage(id: String) {
        if (ids.contains(id)) {
            ids.remove(id)
        } else {
            ids.add(id)
        }
    }

    fun deleteMessages() {
        viewModelScope.launch {
            ids.forEach { id ->
                repository.getChatMessage(id)?.let {
                    _uiState.value.deleteMessage(it.toDefault())
                }
            }

            repository.deleteMessages(ids)
            ids.clear()
        }
    }
}