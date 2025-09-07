package com.tumba.bhaga.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tumba.bhaga.domain.models.SearchEntry
import com.tumba.bhaga.ui.components.SearchStockList
import kotlinx.coroutines.delay
import org.apache.commons.text.similarity.LevenshteinDistance
import kotlin.math.min

@Composable
fun SearchScreen(
    onStockClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = remember { SearchViewModel() },
) {
    val filtered = remember { mutableStateListOf<SearchEntry>() }
    var query by remember { mutableStateOf("") }
    val entries by viewModel.entries.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search Stock ...") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        LaunchedEffect(query) {
            delay(500)
            filtered.clear()
            filtered.addAll(onSearch(query, entries))
        }

        SearchStockList(filtered, onStockClick)
    }
}

private fun onSearch(query: String, entries: List<SearchEntry>): List<SearchEntry> {
    if (query.isEmpty()) {
        return listOf()
    }

    val ld = LevenshteinDistance.getDefaultInstance()

    return entries
        .map {
            it to min(
                ld.apply(it.ticker, query),
                ld.apply(it.companyName, query)
            )
        }
        .sortedBy { it.second }
        .take(10)
        .map { it.first }
}
