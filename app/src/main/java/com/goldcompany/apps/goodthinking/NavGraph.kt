package com.goldcompany.apps.goodthinking

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.goldcompany.apps.goodthinking.goodword.GoodWordScreen
import com.goldcompany.apps.goodthinking.home.HomeScreen

object NavDestinations {
    const val HOME = "home"
    const val GOOD_WORD = "good_word"
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavDestinations.HOME) {
        composable(NavDestinations.HOME) {
            HomeScreen(navController = navController)
        }
        composable(NavDestinations.GOOD_WORD) {
            GoodWordScreen(navController = navController)
        }
    }
}