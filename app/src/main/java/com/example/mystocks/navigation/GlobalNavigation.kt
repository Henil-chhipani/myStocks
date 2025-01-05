package com.example.mystocks.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mystocks.ui.home.HomeScreen
import com.example.mystocks.ui.stockInfo.StockInfoScreen

@Composable
fun GlobalNavigation(navHostController: NavHostController) {
    val context = LocalContext.current
    CompositionLocalProvider(GlobalNavController provides navHostController) {
        NavHost(
            navController = navHostController,
            startDestination = "homeScreen"
        ) {
            composable("homeScreen") {
                HomeScreen()
            }
            composable(
                "stockInfoScreen/{symbol}",
                arguments = listOf(navArgument("symbol") {
                    type = NavType.StringType
                }),

                ) { backStackEntry ->
                val symbol = backStackEntry.arguments?.getString("symbol") ?: ""
                StockInfoScreen(symbol = symbol)
            }
        }
    }

}

val GlobalNavController = staticCompositionLocalOf<NavHostController> {
    error("NavController not provided")
}