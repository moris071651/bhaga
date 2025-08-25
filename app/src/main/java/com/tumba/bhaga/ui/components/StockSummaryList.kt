package com.tumba.bhaga.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tumba.bhaga.domain.models.StockSummary

@Composable
fun StockSummaryList(stocks: List<StockSummary>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        item {
            Spacer(modifier = Modifier.size(8.dp))
        }

        items(stocks) { s ->
            StockSummaryCard(s)

            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}
