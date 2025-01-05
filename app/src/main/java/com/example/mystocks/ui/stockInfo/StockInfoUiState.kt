package com.example.mystocks.ui.stockInfo

import com.example.mystocks.data.entity.IntraDayStockData

data class StockInfoUiState(
    val intraDayStockData: IntraDayStockData? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
