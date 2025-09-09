package com.tumba.bhaga.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tumba.bhaga.domain.models.StockSummary

@Composable
fun StockSummaryList(
    stocks: List<StockSummary>,
    onStockClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(stocks, key = { it.ticker }) { stock ->
            StockSummaryCard(
                stock = stock,
                onClick = {
                    onStockClick(stock.ticker)
                }
            )
        }
    }
}
