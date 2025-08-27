package com.tumba.bhaga.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tumba.bhaga.data.di.AppModule.repository
import com.tumba.bhaga.domain.models.StockSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel() : ViewModel() {
    var stocks by mutableStateOf<List<StockSummary>>(listOf())
        private set

    init {
        val tickers = listOf(
            "TSLA",
            "AAPL",
            "ADBE",
            "AMD",
            "AMZN",
            "BAC",
            "BLK",
            "CAT",
            "CSCO",
            "GOOGL",
            "INTC",
            "MCD",
            "MA",
            "MSFT",
            "NVDA",
            "PYPL",
            "TXN"
        )

        viewModelScope.launch(Dispatchers.IO) {
            val results = tickers.map { ticker ->
                async { repository.getStockSummary(ticker) }
            }.awaitAll()

            withContext(Dispatchers.Main) {
                stocks = results
            }
        }
    }
}