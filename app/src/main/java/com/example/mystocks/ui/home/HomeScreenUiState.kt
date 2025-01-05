package com.example.mystocks.ui.home

import com.example.mystocks.data.entity.ApiResponse
import com.example.mystocks.data.entity.GainersAndLosersData

data class HomeScreenUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val niftyDataLoading: Boolean = false,
    val niftyData: ApiResponse = ApiResponse(),
    val sensexData: ApiResponse = ApiResponse(),
    val niftyGrowthPercentage: String = "N/A",
    val sensexGrowthPercentage: String = "N/A",
    val isRefreshing: Boolean = false,
    val gainersAndLosersData: GainersAndLosersData = GainersAndLosersData()
)