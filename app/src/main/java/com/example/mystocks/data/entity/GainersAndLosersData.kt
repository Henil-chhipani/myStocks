package com.example.mystocks.data.entity

import com.google.gson.annotations.SerializedName

data class GainersAndLosersData(
    val metadata: String = "",
    @SerializedName("last_updated") val lastUpdated: String = "",
    @SerializedName("top_gainers") val topGainers: List<StockData> = emptyList(),
    @SerializedName("top_losers") val topLosers: List<StockData> = emptyList(),
    @SerializedName("most_actively_traded") val mostActivelyTraded: List<StockData> = emptyList()
)

data class StockData(
    val ticker: String = "",
    val price: String = "0.0", // Use String to match the JSON response
    @SerializedName("change_amount") val changeAmount: String = "0.0", // Use String to match JSON
    @SerializedName("change_percentage") val changePercentage: String = "0.0", // Use String to match JSON
    val volume: String = "0" // Use String to match JSON
)
