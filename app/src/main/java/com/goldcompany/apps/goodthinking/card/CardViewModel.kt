package com.goldcompany.apps.goodthinking.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldcompany.apps.goodthinking.data.repo.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val repository: DefaultRepository
) : ViewModel() {

    private val _goodThinking = MutableStateFlow("")
    val goodThinking: StateFlow<String> = _goodThinking

    fun getThinkingCard() {
        viewModelScope.launch(Dispatchers.IO) {
            val card = repository.getThinking()
            _goodThinking.value = card.thinking
        }
    }
}