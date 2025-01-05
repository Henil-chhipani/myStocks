package com.example.mystocks.ui.stockInfo

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun StockInfoScreen(
    viewModel: StockInfoViewModel = hiltViewModel(),
    symbol: String
) {
    val stockInfoUiState by viewModel.uiState.collectAsState()
    Text(symbol, style = MaterialTheme.typography.titleLarge)
    StockInfoUI()
}

@Composable
fun StockInfoUI() {


}

@Preview(showBackground = true)
@Composable
fun StockInfoScreenPreview() {
    StockInfoUI()
}