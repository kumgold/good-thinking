package com.goldcompany.apps.goodthinking.editcard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.goldcompany.apps.goodthinking.data.db.GoodThinking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCardScreen(
    viewModel: EditCardViewModel = hiltViewModel(),
    navController: NavController
) {
    val list by viewModel.thinkingList.collectAsStateWithLifecycle()

    Scaffold (
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {

                        },
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "add"
                        )
                    }
                    IconButton(
                        onClick = {

                        },
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "delete"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            items(list) {
                CardItem(
                    goodThinking = it,
                    updateGoodThinking = { id, thinking ->
                        viewModel.updateGoodThinking(id, thinking)
                    }
                )
            }
        }
    }
}

@Composable
private fun CardItem(
    goodThinking: GoodThinking,
    updateGoodThinking: (Long, String) -> Unit
) {
    val isClicked = rememberSaveable { mutableStateOf(false) }

    if (!isClicked.value) {
        GoodThinkingItem(
            isClicked = isClicked,
            word = goodThinking.thinking
        )
    } else {
        EditableGoodThinkingItem(
            isClicked = isClicked,
            goodThinking = goodThinking,
            editGoodThinking = { id, thinking ->
                updateGoodThinking(id, thinking)
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GoodThinkingItem(
    isClicked: MutableState<Boolean>,
    word: String
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .combinedClickable(
                onClick = {},
                onLongClick = {
                    isClicked.value = !isClicked.value
                }
            )
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = word.trim()
        )
    }
}

@Composable
private fun EditableGoodThinkingItem(
    isClicked: MutableState<Boolean>,
    goodThinking: GoodThinking,
    editGoodThinking: (Long, String) -> Unit
) {
    var word by rememberSaveable { mutableStateOf(goodThinking.thinking.trim()) }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier.padding(10.dp),
            value = word,
            onValueChange = {
                word = it.trim()
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = {
                    editGoodThinking(goodThinking.id, word)
                    isClicked.value = !isClicked.value
                }
            ) {
                Text("확인")
            }
            TextButton(
                onClick = { isClicked.value = !isClicked.value }
            ) {
                Text("취소")
            }
        }
    }
}