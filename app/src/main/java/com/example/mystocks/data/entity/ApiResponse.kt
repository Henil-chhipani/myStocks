package com.example.mystocks.data.entity

data class ApiResponse(
    val chart: Chart = Chart()
)

data class Chart(
    val result: List<Result> = emptyList(),
    val error: Any? = null // Error can be null or an object
)

data class Result(
    val meta: Meta = Meta(),
    val timestamp: List<Long> = emptyList(),
    val indicators: Indicators = Indicators()
)

data class Meta(
    val currency: String = "",
    val symbol: String = "",
    val exchangeName: String = "",
    val fullExchangeName: String = "",
    val instrumentType: String = "",
    val firstTradeDate: Long = 0L,
    val regularMarketTime: Long = 0L,
    val hasPrePostMarketData: Boolean = false,
    val gmtoffset: Int = 0,
    val timezone: String = "",
    val exchangeTimezoneName: String = "",
    val regularMarketPrice: Float = 0f,
    val fiftyTwoWeekHigh: Float = 0f,
    val fiftyTwoWeekLow: Float = 0f,
    val regularMarketDayHigh: Float = 0f,
    val regularMarketDayLow: Float = 0f,
    val regularMarketVolume: Long = 0,
    val longName: String = "",
    val shortName: String = "",
    val chartPreviousClose: Float = 0f,
    val previousClose: Float = 0f,
    val scale: Int = 0,
    val priceHint: Int = 0,
    val currentTradingPeriod: CurrentTradingPeriod = CurrentTradingPeriod(),
    val tradingPeriods: List<List<TradingPeriod>> = emptyList(),
    val dataGranularity: String = "",
    val range: String = "",
    val validRanges: List<String> = emptyList()
)

data class CurrentTradingPeriod(
    val pre: Period = Period(),
    val regular: Period = Period(),
    val post: Period = Period()
)

data class Period(
    val timezone: String = "",
    val end: Long = 0L,
    val start: Long = 0L,
    val gmtoffset: Int = 0
)

data class TradingPeriod(
    val timezone: String = "",
    val end: Long = 0L,
    val start: Long = 0L,
    val gmtoffset: Int = 0
)

data class Indicators(
    val quote: List<Quote> = emptyList()
)

data class Quote(
    val open: List<Float?> = emptyList(),
    val close: List<Float?> = emptyList(),
    val low: List<Float?> = emptyList(),
    val high: List<Float?> = emptyList(),
    val volume: List<Long?> = emptyList()
)
