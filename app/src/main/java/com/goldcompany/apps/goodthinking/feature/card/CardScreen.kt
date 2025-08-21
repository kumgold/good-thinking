package com.goldcompany.apps.goodthinking.feature.card

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
                title = { Text("Good Thinking", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back",
                            tint = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        val isFlipped = rememberSaveable { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            FlipCard(
                isFlipped = isFlipped.value,
                onClick = {
                    isFlipped.value = !isFlipped.value

                    if (isFlipped.value) {
                        getGoodThinking()
                    }
                },
                front = {
                    Icon(
                        imageVector = Icons.Filled.Lightbulb,
                        contentDescription = null,
                        modifier = Modifier.size(56.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                back = {
                    Text(
                        text = goodThinking,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    }
}

@Composable
fun FlipCard(
    isFlipped: Boolean,
    onClick: () -> Unit,
    front: @Composable () -> Unit,
    back: @Composable () -> Unit
) {
    // 1. 화면 구성을 가져오기 위해 LocalConfiguration 사용
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "cardRotation"
    )

    val scale by animateFloatAsState(
        targetValue = if (isFlipped) 1.5f else 1.0f,
        animationSpec = tween(durationMillis = 1000),
        label = "cardScale"
    )

    Card(
        modifier = Modifier
            .width(screenWidth / 2)
            .aspectRatio(3f / 4f)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (rotation <= 90f) {
                front()
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { rotationY = 180f },
                    contentAlignment = Alignment.Center
                ) {
                    back()
                }
            }
        }
    }
}