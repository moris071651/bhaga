package com.tumba.bhaga.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tumba.bhaga.data.repository.SearchRepository
import com.tumba.bhaga.domain.models.SearchEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {
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
