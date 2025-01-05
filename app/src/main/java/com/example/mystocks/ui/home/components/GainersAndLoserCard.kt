package com.example.mystocks.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
fun GainersAndLoserCard(
    title: String,
    price: String,
    percentage: String,
    onClick: () -> Unit,
    changeAmount: String,

    ) {
    val extendedColors = LocalExtendedColors.current
    val percentageValue = changeAmount.toFloatOrNull() ?: 0f
    val percentageColor = if (percentageValue >= 0) {
        extendedColors.green.color
    } else {
        extendedColors.red.color
    }
    Card(
        modifier = Modifier
            .height(143.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(10.dp),

        ) {
        Box(modifier = Modifier.padding(10.dp)) {
            Column(
                modifier = Modifier
                    .width(150.dp)
                    .padding(top = 10.dp, start = 3.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "$" + price, style = MaterialTheme.typography.titleMedium)
                Row {

                    Text(
                        text = changeAmount,
                        style = MaterialTheme.typography.bodyMedium,
                        color = percentageColor
                    )
                    val per = percentage.replace("%", "").toDoubleOrNull()?.let {
                        "%.2f".format(it)
                    } ?: "0.00"
                    Text(
                        text = "($per%)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = percentageColor
                    )
                }
            }
        }
    }
}