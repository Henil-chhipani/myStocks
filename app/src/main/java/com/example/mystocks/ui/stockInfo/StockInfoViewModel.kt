package com.example.mystocks.ui.stockInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystocks.data.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StockInfoViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(StockInfoUiState())
    val uiState: StateFlow<StockInfoUiState> = _uiState.asStateFlow()

    fun onEvent(event: StockInfoEvent) {
        when (event) {
            is StockInfoEvent.fetchStockInfo -> fetchStockInfo(event.symbol)
        }
    }

    private fun fetchStockInfo(symbol: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            repository.getIntraDayDataOfStock(symbol)
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}