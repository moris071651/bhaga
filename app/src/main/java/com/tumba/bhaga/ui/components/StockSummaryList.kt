package com.tumba.bhaga.ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tumba.bhaga.domain.models.StockSummary

@Composable
fun StockSummaryList(
    stocks: List<StockSummary>,
    onStockClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxHeight()
    ) {
        items(stocks, key = {it.ticker}) { s ->
            StockSummaryCard(s, onClick = { onStockClick(s.ticker) })
        }
    }
}
