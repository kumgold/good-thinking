package com.goldcompany.apps.goodthinking.feature.editcard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.goldcompany.apps.goodthinking.R
import com.goldcompany.apps.goodthinking.data.db.goodword.GoodWord


@Composable
fun EditCardScreen(
    viewModel: EditCardViewModel = hiltViewModel(),
    navController: NavController
) {
    val thinkingList by viewModel.thinkingList.collectAsStateWithLifecycle()

    EditCardScreen(
        thinkingList = thinkingList,
        popBackStack = { navController.popBackStack() },
        updateGoodThinking = { data -> viewModel.updateGoodThinking(data.id, data.word) },
        insertGoodThinking = { thinking -> viewModel.insertGoodThinking(thinking)},
        deleteGoodThinking = { id -> viewModel.deleteGoodThinking(id) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCardScreen(
    thinkingList: List<GoodWord>,
    popBackStack: () -> Unit = {},
    updateGoodThinking: (GoodWord) -> Unit = {},
    insertGoodThinking: (String) -> Unit = {},
    deleteGoodThinking: (Long) -> Unit = {}
) {
    val isOpenedDialog = rememberSaveable { mutableStateOf(false) }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            popBackStack()
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
                            isOpenedDialog.value = !isOpenedDialog.value
                        },
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "add"
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
            item {
                if (thinkingList.isEmpty()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.empty_edit_card)
                    )
                }
            }

            items(thinkingList) {
                CardItem(
                    goodWord = it,
                    updateGoodThinking = { data ->
                        updateGoodThinking(data)
                    },
                    deleteGoodThinking = { id ->
                        deleteGoodThinking(id)
                    }
                )
            }
        }
    }

    if (isOpenedDialog.value) {
        var word by rememberSaveable { mutableStateOf("") }

        BasicAlertDialog(
            modifier = Modifier.widthIn(300.dp, 350.dp).aspectRatio(ratio = 16/9f),
            onDismissRequest = { isOpenedDialog.value = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
        ) {
            Card(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxSize().padding(10.dp).weight(3f),
                        value = word,
                        onValueChange = {
                            word = it
                        }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = {
                                insertGoodThinking(word.trim())
                                isOpenedDialog.value = !isOpenedDialog.value
                            },
                            enabled = word.isNotEmpty()
                        ) {
                            Text(stringResource(R.string.confirm))
                        }
                        TextButton(
                            onClick = { isOpenedDialog.value = !isOpenedDialog.value }
                        ) {
                            Text(stringResource(R.string.cancel))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CardItem(
    goodWord: GoodWord,
    updateGoodThinking: (GoodWord) -> Unit,
    deleteGoodThinking: (Long) -> Unit
) {
    val isClicked = rememberSaveable { mutableStateOf(false) }

    if (!isClicked.value) {
        GoodThinkingItem(
            isClicked = isClicked,
            word = goodWord.word
        )
    } else {
        EditableGoodThinkingItem(
            isClicked = isClicked,
            goodWord = goodWord,
            updateGoodThinking = { id, thinking ->
                updateGoodThinking(GoodWord(id, thinking))
            },
            deleteGoodThinking = { id ->
                deleteGoodThinking(id)
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
    goodWord: GoodWord,
    updateGoodThinking: (Long, String) -> Unit,
    deleteGoodThinking: (Long) -> Unit
) {
    var word by rememberSaveable { mutableStateOf(goodWord.word.trim()) }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            value = word,
            onValueChange = {
                word = it
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(
                onClick = {
                    deleteGoodThinking(goodWord.id)
                    isClicked.value = !isClicked.value
                }
            ) {
                Text(stringResource(R.string.delete))
            }
            Spacer(
                modifier = Modifier.weight(1f)
            )
            TextButton(
                onClick = {
                    updateGoodThinking(goodWord.id, word.trim())
                    isClicked.value = !isClicked.value
                }
            ) {
                Text(stringResource(R.string.confirm))
            }
            TextButton(
                onClick = { isClicked.value = !isClicked.value }
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    }
}