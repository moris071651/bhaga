package com.tumba.bhaga.ui.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tumba.bhaga.data.di.AppModule.repository
import com.tumba.bhaga.domain.models.SearchEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class SearchViewModel() : ViewModel() {
    private val _entries = MutableStateFlow<List<SearchEntry>>(listOf())
    val entries: StateFlow<List<SearchEntry>> = _entries

    init {
        getAllSearchEntries()
    }

    private fun getAllSearchEntries() {
        viewModelScope.launch {
            _entries.value = repository.getAllSearchEntries()
        }
    }
}
