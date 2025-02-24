package com.goldcompany.apps.goodthinking.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldcompany.apps.goodthinking.data.repo.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val repository: DefaultRepository
) : ViewModel() {
    fun getThinkingCard() {
        viewModelScope.launch(Dispatchers.IO) {
            val card = repository.getThinking()
            println(card.thinking)
        }
    }
}