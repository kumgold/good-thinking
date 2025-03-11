package com.goldcompany.apps.goodthinking.goodthinking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldcompany.apps.goodthinking.BuildConfig
import com.goldcompany.apps.goodthinking.UiState
import com.goldcompany.apps.goodthinking.data.repo.DefaultRepository
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoodThinkingViewModel @Inject constructor(
    private val repository: DefaultRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = BuildConfig.apiKey
    )

    init {
        sendPrompt()
    }

    fun sendPrompt(prompt: String? = null) {
        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(
                    content {
                        text(prompt ?: "오늘의 명언 하나만 보여줘")
                    }
                )
                response.text?.let { outputContent ->
                    _uiState.value = UiState.Success(outputContent)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "")
            }
        }
    }

    fun insertGoodWord() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_uiState.value is UiState.Success) {
                val word = (_uiState.value as UiState.Success).outputText
                repository.insertGoodWord(word)
            }
        }
    }
}