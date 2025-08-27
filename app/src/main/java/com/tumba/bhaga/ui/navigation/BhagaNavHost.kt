package com.tumba.bhaga.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tumba.bhaga.ui.screens.home.HomeScreen
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
                onStockClick = { ticker: String ->
                    println(ticker)
//                    navController.navigate("details/$ticker")
                }
            )
        }
        composable(
            route = "details/{ticker}",
            arguments = listOf(navArgument("ticker") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val ticker = backStackEntry.arguments?.getString("ticker")!!
            StockDetailScreen(ticker = ticker)
        }
    }
}
