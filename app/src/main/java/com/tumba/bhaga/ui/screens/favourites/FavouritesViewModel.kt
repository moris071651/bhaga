package com.tumba.bhaga.ui.screens.favourites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tumba.bhaga.data.di.AppModule.repository
import com.tumba.bhaga.domain.models.StockSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavouritesViewModel() : ViewModel() {

    private val _favourites = MutableStateFlow<List<StockSummary>>(emptyList())
    val favourites: StateFlow<List<StockSummary>> = _favourites

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val list = repository.getFavouriteCompanies()
                _favourites.value = list
            }
            catch (e: Exception) {
                _favourites.value = emptyList()
            }
            finally {
                _isLoading.value = false
            }
        }
    }
}
