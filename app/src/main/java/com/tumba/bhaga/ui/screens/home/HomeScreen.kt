package com.tumba.bhaga.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tumba.bhaga.ui.components.StockSummaryList

@Composable
fun HomeScreen(
    onStockClick: (String) -> Unit,
    viewModel: HomeViewModel = HomeViewModel()
) {
    val stocks = viewModel.stocks

    if (stocks.isEmpty()) {
        // Loading state
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        StockSummaryList(stocks, onStockClick)
    }
}
