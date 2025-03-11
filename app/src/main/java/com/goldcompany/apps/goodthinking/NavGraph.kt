package com.goldcompany.apps.goodthinking

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.goldcompany.apps.goodthinking.card.CardScreen
import com.goldcompany.apps.goodthinking.editcard.EditCardScreen
import com.goldcompany.apps.goodthinking.goodthinking.GoodThinkingScreen
import com.goldcompany.apps.goodthinking.home.HomeScreen

object NavDestinations {
    const val HOME = "home"
    const val GOOD_WORD = "good_word"
    const val CARD = "card"
    const val EDIT_CARD = "edit_card"
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavDestinations.HOME) {
        composable(NavDestinations.HOME) {
            HomeScreen(navController = navController)
        }
        composable(NavDestinations.GOOD_WORD) {
            GoodThinkingScreen(navController = navController)
        }
        composable(NavDestinations.CARD) {
            CardScreen(navController = navController)
        }
        composable(NavDestinations.EDIT_CARD) {
            EditCardScreen(navController = navController)
        }
    }
}