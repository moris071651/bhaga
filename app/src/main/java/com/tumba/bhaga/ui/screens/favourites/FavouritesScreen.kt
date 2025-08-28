package com.tumba.bhaga.ui.screens.favourites

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tumba.bhaga.ui.components.StockSummaryList

@Composable
fun FavouritesScreen(
    onStockClick: (String) -> Unit,
    viewModel: FavouritesViewModel = FavouritesViewModel()
) {
    val favourites by viewModel.favourites.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    when {
        isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        favourites.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No favourites yet")
            }
        }
        else -> {
            StockSummaryList(favourites, onStockClick)
        }
    }
}


