package com.tumba.bhaga.ui.screens.stockdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tumba.bhaga.data.di.AppModule.repository
import com.tumba.bhaga.domain.models.StockDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StockDetailViewModel : ViewModel() {
    private val _stock = MutableStateFlow<StockDetail?>(null)
    val stock: StateFlow<StockDetail?> = _stock

    fun loadStock(ticker: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _stock.value = repository.getStockDetail(ticker)
        }
    }
}
