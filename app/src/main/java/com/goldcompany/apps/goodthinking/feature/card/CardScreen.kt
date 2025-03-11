package com.goldcompany.apps.goodthinking.feature.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun CardScreen(
    navController: NavController,
    viewModel: CardViewModel = hiltViewModel()
) {
    val goodThinking by viewModel.goodWord.collectAsStateWithLifecycle()

    CardScreen(
        goodThinking = goodThinking,
        popBackStack = { navController.popBackStack() },
        getGoodThinking = { viewModel.getGoodThinking() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardScreen(
    goodThinking: String,
    popBackStack: () -> Unit = {},
    getGoodThinking: () -> Unit = {}
) {
    Scaffold(
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
                }
            )
        }
    ) { paddingValues ->
        val isCardOpened = rememberSaveable { mutableStateOf(false) }

        LazyVerticalGrid(
            modifier = Modifier.padding(paddingValues),
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp),
        ) {
            items(15) {
                GoodThinkingCard(
                    onClick = {
                        getGoodThinking()
                        isCardOpened.value = true
                    }
                )
            }
        }

        if (isCardOpened.value) {
            BasicAlertDialog(
                modifier = Modifier.widthIn(300.dp, 350.dp).aspectRatio(ratio = 9/16f),
                onDismissRequest = { isCardOpened.value = false },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false
                ),
            ) {
                Card(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                            .wrapContentSize(),
                        text = goodThinking,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun GoodThinkingCard(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(width = 90.dp, height = 160.dp)
            .clickable { onClick() }
    ) {}
}