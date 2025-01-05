package com.example.mystocks.data.repository

import com.example.mystocks.data.api.AlphaVantageApi
import com.example.mystocks.data.api.FinanceApiService
import com.example.mystocks.data.api.YahooFinanceApi
import javax.inject.Inject

class StockRepository @Inject constructor(
    private val api: FinanceApiService,
    private val alphaVantageApi: AlphaVantageApi
) {
    suspend fun getNifty50Data() = api.getStockData(
        symbol = "^NSEI",
        interval = "1d",
        range = "1mo"
    )

    suspend fun getStockData(symbol: String, interval: String, range: String) = api.getStockData(
        symbol = symbol,
        interval = interval,
        range = range
    )

    suspend fun getIntraDayDataOfStock(symbol: String) =
        alphaVantageApi.getIntraDayDataOfStock(symbol = symbol)

    suspend fun getTopGainersAndLosers() = alphaVantageApi.getGainersAndLosers()
}