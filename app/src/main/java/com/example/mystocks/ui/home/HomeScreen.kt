package com.example.mystocks.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mystocks.R
import com.example.mystocks.navigation.GlobalNavController
import com.example.mystocks.ui.home.components.GainersAndLoserCard
import com.example.mystocks.ui.home.components.StockCard
import com.example.mystocks.ui.theme.AppTheme
import com.example.mystocks.ui.theme.LocalExtendedColors
import com.example.mystocks.ui.theme.ui_horizontalPadding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeScreenUiState by viewModel.uiState.collectAsState()
    HomeScreenUI(
        uiState = homeScreenUiState,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUI(
    uiState: HomeScreenUiState,
    onEvent: (HomeScreenEvent) -> Unit
) {
    val globalNavController = GlobalNavController.current
    LaunchedEffect(Unit) {
        onEvent(HomeScreenEvent.FetchData)
    }

    val refreshState = rememberPullToRefreshState()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .padding(horizontal = ui_horizontalPadding)
                    .padding(top = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.appicon),
                    contentDescription = "Home",
                    modifier = Modifier.size(35.dp),
                )
                Text(
                    text = "Stocks",
                    modifier = Modifier.padding(start = 10.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search",
                        modifier = Modifier.padding(start = 10.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        content = { paddingValues ->
            PullToRefreshBox(
                state = refreshState,
                isRefreshing = uiState.isRefreshing,
                onRefresh = {
                    onEvent(HomeScreenEvent.RefreshData) // Trigger data refresh
                }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(top = paddingValues.calculateTopPadding() + 20.dp)
                ) {
                    item {
                        Row {
                            Box(
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                StockCard(
                                    title = "NIFTY",
                                    price = uiState.niftyData.chart.result.firstOrNull()?.meta?.regularMarketPrice?.toString()
                                        ?: "0.0",
                                    percentage = uiState.niftyGrowthPercentage,
                                )
                            }
                            Box(
                                modifier = Modifier.padding(end = 7.dp)
                            ) {
                                StockCard(
                                    title = "SENSEX",
                                    price = uiState.sensexData.chart.result.firstOrNull()?.meta?.regularMarketPrice?.toString()
                                        ?: "0.0",
                                    percentage = uiState.sensexGrowthPercentage,
                                )
                            }
                        }
                    }
                    item {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .padding(horizontal = ui_horizontalPadding),
                        ) {
                            Text(
                                text = "Top Gainers: US",
                                modifier = Modifier,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = "Arrow",
                                modifier = Modifier.size(30.dp),
                            )
                        }
                        LazyRow(
                            modifier = Modifier.padding(top = 10.dp)
                        ) {
                            items(uiState.gainersAndLosersData.topGainers.size) { index ->
                                val stock = uiState.gainersAndLosersData.topGainers[index]
                                Box(
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
                                    GainersAndLoserCard(
                                        title = stock.ticker,
                                        price = stock.price.toString(),
                                        changeAmount = "+" + stock.changeAmount,
                                        percentage = stock.changePercentage.toString(),
                                        onClick = {
                                            onEvent(
                                                HomeScreenEvent.NavigateToStockDetails(
                                                    symbol = stock.ticker,
                                                    globalNavController = globalNavController
                                                )
                                            )
                                        },
                                    )
                                }
                            }
                        }
                    }
                    item {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .padding(horizontal = ui_horizontalPadding),
                        ) {
                            Text(
                                text = "Top losers: US  ",
                                modifier = Modifier,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = "Arrow",
                                modifier = Modifier.size(30.dp),
                            )
                        }
                        LazyRow(
                            modifier = Modifier.padding(top = 10.dp)
                        ) {
                            items(uiState.gainersAndLosersData.topLosers.size) { index ->
                                val stock = uiState.gainersAndLosersData.topLosers[index]
                                Box(
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
                                    GainersAndLoserCard(
                                        title = stock.ticker,
                                        price = stock.price.toString(),
                                        changeAmount = stock.changeAmount,
                                        percentage = stock.changePercentage.toString(),
                                        onClick = {
                                            onEvent(
                                                HomeScreenEvent.NavigateToStockDetails(
                                                    symbol = stock.ticker,
                                                    globalNavController = globalNavController
                                                )
                                            )
                                        },
                                    )
                                }
                            }
                        }
                    }
                    item {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .padding(horizontal = ui_horizontalPadding),
                        ) {
                            Text(
                                text = "Most traded stocks: US ",
                                modifier = Modifier,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = "Arrow",
                                modifier = Modifier.size(30.dp),
                            )
                        }
                        LazyRow(
                            modifier = Modifier.padding(top = 10.dp)
                        ) {
                            items(uiState.gainersAndLosersData.mostActivelyTraded.size) { index ->
                                val stock = uiState.gainersAndLosersData.mostActivelyTraded[index]
                                Box(
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
                                    GainersAndLoserCard(
                                        title = stock.ticker,
                                        price = stock.price.toString(),
                                        onClick = {
                                            onEvent(
                                                HomeScreenEvent.NavigateToStockDetails(
                                                    symbol = stock.ticker,
                                                    globalNavController = globalNavController
                                                )
                                            )
                                        },
                                        changeAmount = stock.changeAmount,
                                        percentage = stock.changePercentage.toString(),
                                    )
                                }
                            }
                        }
                    }
                    item {
                        Spacer(Modifier.height(100.dp))
                    }

                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            HomeScreenUI(
                uiState = HomeScreenUiState(),
                onEvent = {}
            )
        }
    }
}