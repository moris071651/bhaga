package com.tumba.bhaga.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tumba.bhaga.domain.models.SearchEntry
import com.tumba.bhaga.domain.models.StockSummary

@Composable
fun SearchStockList(
    stocks: List<SearchEntry>,
    onStockClick: (String) -> Unit,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(stocks, key = { it.ticker }) {
            SearchStockItem(
                stock = it,
                onClick = {
                    onStockClick(it.ticker)
                }
            )
        }
    }
}
