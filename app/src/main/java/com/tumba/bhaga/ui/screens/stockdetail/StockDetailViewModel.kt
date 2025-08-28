package com.tumba.bhaga.ui.screens.stockdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tumba.bhaga.data.di.AppModule.repository
import com.tumba.bhaga.domain.models.StockDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StockDetailViewModel() : ViewModel() {

    private val _stock = MutableStateFlow<StockDetail?>(null)
    val stock: StateFlow<StockDetail?> = _stock

    private val _isFavourite = MutableStateFlow(false)
    val isFavourite: StateFlow<Boolean> = _isFavourite

    fun loadStock(ticker: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _stock.value = repository.getStockDetail(ticker)

            // Check if this stock is already a favourite
            val favourites = repository.getFavouriteCompanies()
            _isFavourite.value = favourites.any { it.ticker == ticker }
        }
    }

    fun addToFavourites() {
        viewModelScope.launch(Dispatchers.IO) {
            _stock.value?.ticker?.let {
                repository.addFavourite(it)
                _isFavourite.value = true
            }
        }
    }

    fun removeFromFavourites() {
        viewModelScope.launch(Dispatchers.IO) {
            _stock.value?.ticker?.let {
                repository.removeFavourite(it)
                _isFavourite.value = false
            }
        }
    }
}
