package com.tumba.bhaga.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tumba.bhaga.data.di.AppModule
import com.tumba.bhaga.domain.models.SearchResult
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _query = MutableStateFlow("")

    var query by mutableStateOf("")
        private set

    var searchResults by mutableStateOf<List<SearchResult>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    init {
        @OptIn(FlowPreview::class)
        viewModelScope.launch {
            _query.debounce(300).collect { q ->
                if (q.isNotBlank()) {
                    search(q)
                } else {
                    searchResults = emptyList()
                }
            }
        }
    }

    private fun search(query: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                searchResults = AppModule.repository.searchSymbols(query)
            } catch (e: Exception) {
                // Handle error, e.g., show a toast or log the error
                println("Search error: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun onQueryChanged(newQuery: String) {
        query = newQuery
        _query.value = newQuery
    }
}
