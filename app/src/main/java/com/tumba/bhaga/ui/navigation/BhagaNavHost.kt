package com.tumba.bhaga.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tumba.bhaga.ui.screens.favourites.FavouritesScreen
import com.tumba.bhaga.ui.screens.home.HomeScreen
import com.tumba.bhaga.ui.screens.search.SearchScreen
import com.tumba.bhaga.ui.screens.settings.SettingsScreen
import com.tumba.bhaga.ui.screens.stockdetail.StockDetailScreen

@Composable
fun BhagaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(
                onStockClick = { ticker ->
                    navController.navigate("detail/$ticker")
                }
            )
        }
        composable("favourites") {
            FavouritesScreen(
                onStockClick = { ticker ->
                    navController.navigate("detail/$ticker")
                }
            )
        }
        composable("settings") {
            SettingsScreen()
        }
        composable("search") {
            SearchScreen(
                onStockClick = { ticker ->
                    navController.navigate("detail/$ticker")
                }
            )
        }
        composable("detail/{ticker}") { backStackEntry ->
            StockDetailScreen(
                ticker = backStackEntry.arguments?.getString("ticker") ?: ""
            )
        }
    }
}
