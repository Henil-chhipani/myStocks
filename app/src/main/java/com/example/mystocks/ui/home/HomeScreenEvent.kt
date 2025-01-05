package com.example.mystocks.ui.home

import androidx.navigation.NavHostController

sealed class HomeScreenEvent {
    object FetchData : HomeScreenEvent()
    object RefreshData : HomeScreenEvent()
    data class NavigateToStockDetails(
        val symbol: String,
        val globalNavController: NavHostController
    ) : HomeScreenEvent()
}