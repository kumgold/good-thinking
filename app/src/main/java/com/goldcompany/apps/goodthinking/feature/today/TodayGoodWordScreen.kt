package com.goldcompany.apps.goodthinking.feature.today

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.goldcompany.apps.goodthinking.R
import com.goldcompany.apps.goodthinking.UiState
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayGoodWordScreen(
    viewModel: TodayGoodWordViewModel = hiltViewModel(),
    navController: NavController
) {
    var isVisible by rememberSaveable { mutableStateOf(false) }
    var isSaved by rememberSaveable { mutableStateOf(false) }
    var result by rememberSaveable { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    LaunchedEffect(isVisible) {
        if (!isVisible) {
            delay(1200L)
            isVisible = true
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(initialAlpha = 0.0f)
            ) {
                TopAppBar(
                    title = {
                        Text(
                            "오늘의 좋은 생각",
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "back",
                                tint = MaterialTheme.colorScheme.primaryContainer
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                viewModel.insertGoodWord()
                                isSaved = true
                            },
                            enabled = !isSaved && (uiState is UiState.Success)
                        ) {
                            Icon(
                                Icons.Default.AddCircle,
                                contentDescription = "add",
                                tint = MaterialTheme.colorScheme.primaryContainer
                            )
                        }
                    }
                )
            }
        },
        bottomBar = {
            var prompt by rememberSaveable { mutableStateOf("") }

            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(initialAlpha = 0.0f)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    value = prompt,
                    onValueChange = { prompt = it },
                    label = { Text(stringResource(R.string.label_prompt)) },
                    leadingIcon = {
                        Icon(Icons.Default.AddCircle, contentDescription = null)
                    },
                    shape = MaterialTheme.shapes.medium,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            viewModel.sendPrompt(prompt.trim())
                        }
                    )
                )
            }
        }
    ) { padding ->
        var prompt by rememberSaveable { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (uiState) {
                is UiState.Loading -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Text(
                            text = "잠시만 기다려 주세요...",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
                }

                is UiState.Error -> {
                    result = (uiState as UiState.Error).errorMessage
                    ResultCard(text = result, color = MaterialTheme.colorScheme.error)
                }

                is UiState.Success -> {
                    result = (uiState as UiState.Success).outputText
                    ResultCard(text = result, color = MaterialTheme.colorScheme.primary)
                }

                else -> {}
            }
        }
    }
}

@Composable
private fun ResultCard(text: String, color: Color) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = MaterialTheme.shapes.large,
        elevation = androidx.compose.material3.CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = color,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(24.dp)
        )
    }
}