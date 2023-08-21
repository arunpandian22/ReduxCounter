package com.example.primecounter.utils

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.primecounter.ui.CounterScreen
import com.example.primecounter.ui.FavouriteScreen
import com.example.primecounter.ui.ListScreen
import com.example.primecounter.ui.RecentActivityScreen


/**
 * Created by Arun Pandian  on 16/08/23.
 */
@Composable
 fun CounterApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = ListScreen.route) {
        composable(route = ListScreen.route) {
            ListScreen(navController)
        }
        composable(route = ActivityScreen.route) {
            RecentActivityScreen()
        }
        composable(route = FavouriteScreen.route) {
            FavouriteScreen()
        }
        composable(route = CounterScreen.route) {
            CounterScreen()
        }
    }
}