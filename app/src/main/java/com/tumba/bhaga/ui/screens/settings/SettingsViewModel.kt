package com.tumba.bhaga.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tumba.bhaga.data.di.AppModule.repository
import com.tumba.bhaga.data.di.AppModule.tokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel() : ViewModel() {
    private val _isTokenValid = MutableStateFlow<Boolean?>(null)
    val isTokenValid: StateFlow<Boolean?> = _isTokenValid

    private val _tokenInitial = MutableStateFlow("")
    val tokenInitial: StateFlow<String> = _tokenInitial

    init {
        getToken()
    }

    private fun getToken() {
        viewModelScope.launch {
            _tokenInitial.value = tokenManager.getToken()
        }
    }

    fun clearStockCache() {
        viewModelScope.launch {
            repository.invalidateAllStockData()
        }
    }

    fun clearCompanyCache() {
        viewModelScope.launch {
            repository.invalidateAllCompanyData()
        }
    }

    fun clearNewsCache() {
        viewModelScope.launch {
            repository.invalidateAllNewsData()
        }
    }

    fun checkTokenValidity(token: String) {
        viewModelScope.launch {
            _isTokenValid.value = tokenManager.checkToken(token)
        }
    }

    fun setNewToken(token: String) {
        viewModelScope.launch {
            tokenManager.saveToken(token)
        }
    }
}
