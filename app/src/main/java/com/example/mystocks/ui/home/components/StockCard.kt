package com.example.mystocks.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mystocks.ui.theme.LocalExtendedColors
import com.example.mystocks.utils.Utils

@Composable
fun StockCard(
    title: String,
    price: String,
    percentage: String
) {
    val extendedColors = LocalExtendedColors.current
    val percentageValue = percentage.toFloatOrNull() ?: 0f
    val percentageColor = if (percentageValue >= 0) {
        extendedColors.green.color
    } else {
        extendedColors.red.color
    }
    Card(
        modifier = Modifier
            .height(70.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Box(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.width(150.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(text = price, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = percentage,
                        style = MaterialTheme.typography.bodyMedium,
                        color = percentageColor
                    )
                }
            }
        }
    }
}