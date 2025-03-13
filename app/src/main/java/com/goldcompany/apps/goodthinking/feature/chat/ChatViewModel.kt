package com.goldcompany.apps.goodthinking.feature.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldcompany.apps.goodthinking.data.db.chat.toDefault
import com.goldcompany.apps.goodthinking.data.repo.DefaultRepository
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.asTextOrNull
import com.google.ai.client.generativeai.type.content
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

    init {
        viewModelScope.launch {
            val messages = repository.getAllMessages().map { it.toDefault() }

            initializeChatMessages(messages)
        }
    }

    private fun initializeChatMessages(messages: List<ChatMessage>) {
        val history = messages.map { message ->
            val participant = if (message.participant == Participant.USER) "user" else "model"

            content(role = participant) { text(message.text) }
        }

        chat = generativeModel.startChat(history = history)

        _uiState.value = ChatUiState(chat.history.map { content ->
            ChatMessage(
                text = content.parts.first().asTextOrNull() ?: "",
                participant = if (content.role == "user") Participant.USER else Participant.MODEL,
                isPending = false
            )
        })
    }


    private val _uiState: MutableStateFlow<ChatUiState> = MutableStateFlow(ChatUiState(emptyList()))
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    fun sendMessage(userMessage: String) {
        val message = ChatMessage(
            text = userMessage,
            participant = Participant.USER,
            isPending = true
        )

        _uiState.value.addMessage(message)

        viewModelScope.launch {
            try {
                repository.insertMessage(message.toLocal())

                val response = chat.sendMessage(userMessage)

                _uiState.value.replaceLastPendingMessage()

                response.text?.let { modelResponse ->
                    val modelMessage = ChatMessage(
                        text = modelResponse,
                        participant = Participant.MODEL,
                        isPending = false
                    )

                    _uiState.value.addMessage(modelMessage)
                    repository.insertMessage(modelMessage.toLocal())
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
}