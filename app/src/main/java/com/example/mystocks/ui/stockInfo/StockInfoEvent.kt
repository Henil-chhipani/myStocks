package com.example.mystocks.ui.stockInfo

sealed class StockInfoEvent {
    data class fetchStockInfo(val symbol: String) : StockInfoEvent()
}