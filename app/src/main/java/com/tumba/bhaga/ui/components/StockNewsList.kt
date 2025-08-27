package com.tumba.bhaga.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tumba.bhaga.domain.models.StockNews

@Composable
fun StockNewsList(newsList: List<StockNews>, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(
            items = newsList,
            key = { it.title }
        ) { news ->
            StockNewsCard(news)
        }
    }
}
