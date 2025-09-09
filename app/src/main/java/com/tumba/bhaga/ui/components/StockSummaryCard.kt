package com.tumba.bhaga.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tumba.bhaga.domain.models.StockSummary

@Composable
fun StockSummaryCard(
    stock: StockSummary,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val logoUrl = remember(stock.logoUrl) { stock.logoUrl }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .clickable(onClick = onClick)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompanyTag(
                stock.ticker,
                stock.companyName,
                stock.logoUrl,
                Modifier.fillMaxHeight()
            )

            Spacer(Modifier.weight(1f))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("% .2f".format(stock.currentPrice))

                PercentChangeBadge(
                    stock.percentChange,
                    stock.isPositiveChange,
                )
            }
        }
    }
}
