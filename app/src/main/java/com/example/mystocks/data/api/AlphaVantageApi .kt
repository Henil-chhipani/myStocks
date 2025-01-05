package com.example.mystocks.data.api


import com.example.mystocks.BuildConfig
import com.example.mystocks.data.entity.GainersAndLosersData
import com.example.mystocks.data.entity.IntraDayStockData
import com.google.gson.internal.GsonBuildConfig
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface AlphaVantageApi  {

    @GET("query")
   suspend fun getGainersAndLosers(
        @Query("function") function: String = "TOP_GAINERS_LOSERS",
        @Query("apikey") apiKey: String = BuildConfig.API_KEY_ALPHAVANTAGE
    ): GainersAndLosersData

   @GET("query")
   suspend fun getIntraDayDataOfStock(
        @Query("function") function: String = "TIME_SERIES_INTRADAY",
        @Query("symbol") symbol: String,
        @Query("interval") interval: String = "5min",
        @Query("apikey") apiKey: String = BuildConfig.API_KEY_ALPHAVANTAGE
    ): IntraDayStockData


}
