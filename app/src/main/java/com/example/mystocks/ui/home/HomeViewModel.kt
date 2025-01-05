package com.example.mystocks.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.mystocks.data.entity.ApiResponse
import com.example.mystocks.data.entity.GainersAndLosersData
import com.example.mystocks.data.repository.StockRepository
import com.example.mystocks.navigation.GlobalNavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.FetchData -> {
                fetchStockData(stockSymbol = "^NSEI", interval = "1m", range = "1d")
                fetchStockData(stockSymbol = "^BSESN", interval = "1m", range = "1d")
                getTopGainersAndLosers()
            }

            is HomeScreenEvent.RefreshData -> refreshData()
            is HomeScreenEvent.NavigateToStockDetails -> navigateToStockDetails(
                event.symbol,
                event.globalNavController
            )
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)
            fetchStockData(stockSymbol = "^NSEI", interval = "1m", range = "1d")
            fetchStockData(stockSymbol = "^BSESN", interval = "1m", range = "1d")
            _uiState.value = _uiState.value.copy(isRefreshing = false)
        }
    }

    private fun navigateToStockDetails(symbol: String, globalNavController: NavHostController) {
        globalNavController.navigate("stockInfoScreen/{$symbol}")
    }

    private fun getTopGainersAndLosers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = repository.getTopGainersAndLosers()
                Log.e("data", "data: ${data}")
                _uiState.value = _uiState.value.copy(gainersAndLosersData = data)
            } catch (e: Exception) {
                Log.e("error", "error: ${e.message}")
            }
        }
    }

    private fun fetchStockData(stockSymbol: String, interval: String, range: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                val data = repository.getStockData(stockSymbol, interval, range)

                // Update the respective stock data
                if (stockSymbol == "^NSEI") {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        niftyData = data
                    )
                    val regularPrice =
                        _uiState.value.niftyData.chart.result.firstOrNull()?.meta!!.regularMarketPrice
                    val previousClosePrice =
                        _uiState.value.niftyData.chart.result.firstOrNull()?.meta!!.chartPreviousClose
                    calculateStockGrowthPercentage(
                        regularPrice,
                        previousClosePrice
                    ) { growthPercentage ->
                        _uiState.value =
                            _uiState.value.copy(niftyGrowthPercentage = growthPercentage.toString())
                    }
                } else if (stockSymbol == "^BSESN") {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        sensexData = data
                    )
                    val regularPrice =
                        _uiState.value.sensexData.chart.result.firstOrNull()?.meta!!.regularMarketPrice
                    val previousClosePrice =
                        _uiState.value.sensexData.chart.result.firstOrNull()?.meta!!.chartPreviousClose
                    calculateStockGrowthPercentage(
                        regularPrice,
                        previousClosePrice
                    ) { growthPercentage ->
                        _uiState.value =
                            _uiState.value.copy(sensexGrowthPercentage = growthPercentage.toString())
                    }

                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    private fun calculateStockGrowthPercentage(
        currentPrice: Float,
        previousClosePrice: Float,
        onResult: (Float) -> Unit
    ) {
        return if (previousClosePrice > 0) {
            val growth = ((currentPrice - previousClosePrice) / previousClosePrice) * 100
            val roundedGrowth = "%.2f".format(growth).toFloat()
            onResult(roundedGrowth)
        } else {
            onResult(0.0f)
        }
    }


}