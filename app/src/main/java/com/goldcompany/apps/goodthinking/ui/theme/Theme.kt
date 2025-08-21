package com.goldcompany.apps.goodthinking.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = DarkGreenPrimary,
    onPrimary = OnDarkGreenPrimary,
    primaryContainer = DarkGreenContainer,
    onPrimaryContainer = OnDarkGreenContainer,
    background = DarkBackground,
    onBackground = PrimaryTextDark,
    surface = DarkSurface,
    onSurface = PrimaryTextDark,
    surfaceVariant = DarkSurface,
    onSurfaceVariant = SecondaryTextDark
)

private val LightColorScheme = lightColorScheme(
    primary = CalmGreenPrimary,
    onPrimary = OnCalmGreenPrimary,
    primaryContainer = CalmGreenContainer,
    onPrimaryContainer = OnCalmGreenContainer,
    background = AppBackground,
    onBackground = PrimaryText,
    surface = CardSurface,
    onSurface = PrimaryText,
    surfaceVariant = CardSurface,
    onSurfaceVariant = SecondaryText
)

@Composable
fun GoodThinkingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}