package com.goldcompany.apps.goodthinking.editcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldcompany.apps.goodthinking.data.repo.DefaultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCardViewModel @Inject constructor(
    private val repository: DefaultRepository
) : ViewModel() {

    val thinkingList = repository.getAllThinking()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun updateGoodThinking(id: Long, thinking: String) {
        viewModelScope.launch {
            repository.updateGoodThinking(id, thinking)
        }
    }
}