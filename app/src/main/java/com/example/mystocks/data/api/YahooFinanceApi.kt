package com.example.mystocks.data.api

import com.example.mystocks.data.entity.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Path

interface YahooFinanceApi {
    @GET("v8/finance/chart/^NSEI")
    suspend fun getNifty50Data(
        @Query("interval") interval: String = "1m", // Interval for the data (e.g., "1d" for daily)
        @Query("range") range: String = "1d"      // Range for the data (e.g., "1d" for one day)
    ): ApiResponse
}
interface FinanceApiService {
    @GET("v8/finance/chart/{symbol}")
    suspend fun getStockData(
        @Path("symbol") symbol: String,       // Dynamic symbol parameter
        @Query("interval") interval: String = "1m", // Interval for the data (default: "1d")
        @Query("range") range: String = "1d"        // Range for the data (default: "1d")
    ): ApiResponse
}