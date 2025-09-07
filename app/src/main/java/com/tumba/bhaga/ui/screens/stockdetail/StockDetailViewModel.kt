package com.tumba.bhaga.ui.screens.stockdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tumba.bhaga.data.repository.FavouritesRepository
import com.tumba.bhaga.data.repository.StockRepository
import com.tumba.bhaga.domain.models.StockDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockDetailViewModel @Inject constructor(
    private val repository: StockRepository,
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {

    private val _stock = MutableStateFlow<StockDetail?>(null)
    val stock: StateFlow<StockDetail?> = _stock

    private val _isFavourite = MutableStateFlow(false)
    val isFavourite: StateFlow<Boolean> = _isFavourite

    fun loadStock(ticker: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _stock.value = repository.getStockDetail(ticker)
            _isFavourite.value = favouritesRepository.checkFavourite(ticker)
        }
    }

    fun addToFavourites() {
        viewModelScope.launch(Dispatchers.IO) {
            _stock.value?.ticker?.let {
                favouritesRepository.addFavourite(it)
                _isFavourite.value = true
            }
        }
    }

    fun removeFromFavourites() {
        viewModelScope.launch(Dispatchers.IO) {
            _stock.value?.ticker?.let {
                favouritesRepository.removeFavourite(it)
                _isFavourite.value = false
            }
        }
    }
}
