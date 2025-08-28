package com.tumba.bhaga.ui.screens.stockdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun StockDetailScreen(
    ticker: String,
    viewModel: StockDetailViewModel = remember { StockDetailViewModel() }
) {
    val stock by viewModel.stock.collectAsState()

    LaunchedEffect(ticker) {
        viewModel.loadStock(ticker)
    }

    if (stock == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header Card
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = stock!!.logoUrl,
                            contentDescription = stock!!.companyName,
                            modifier = Modifier.size(48.dp).clip(CircleShape)
                        )
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(stock!!.companyName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(stock!!.ticker, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                "$${"%.2f".format(stock!!.currentPrice)}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "${if (stock!!.isPositiveChange) "+" else "-"}${"%.2f".format(stock!!.priceChange)} (${ "%.2f".format(stock!!.percentChange)}%)",
                                color = if (stock!!.isPositiveChange) Color(0xFF4CAF50) else Color(0xFFF44336),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        // Change badge
                        Surface(
                            color = if (stock!!.isPositiveChange) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    imageVector = if (stock!!.isPositiveChange) Icons.Default.ThumbUp else Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    tint = if (stock!!.isPositiveChange) Color(0xFF4CAF50) else Color(0xFFF44336),
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "${"%.2f".format(stock!!.percentChange)}%",
                                    color = if (stock!!.isPositiveChange) Color(0xFF4CAF50) else Color(0xFFF44336),
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Price info card
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    InfoRow("Open", "$${"%.2f".format(stock!!.openPrice)}")
                    InfoRow("High", "$${"%.2f".format(stock!!.highPrice)}")
                    InfoRow("Low", "$${"%.2f".format(stock!!.lowPrice)}")
                    InfoRow("Previous Close", "$${"%.2f".format(stock!!.previousClosePrice)}")
                }
            }

            Spacer(Modifier.height(16.dp))

            // Company info card
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    stock!!.website?.let { InfoRow("Website", it) }
                    stock!!.industry?.let { InfoRow("Industry", it) }
                    stock!!.exchange?.let { InfoRow("Exchange", it) }
                    stock!!.country?.let { InfoRow("Country", it) }
                    stock!!.currency?.let { InfoRow("Currency", it) }
                }
            }
        }
    }
}


//@Composable
//fun InfoRow(label: String, value: String) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
//        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
//    }
//}

//@Composable
//fun StockInfoScreen(stock: StockDetail) {
//    Column(modifier = Modifier
//        .fillMaxSize()
//        .padding(16.dp)
//    ) {
//        // Header
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            AsyncImage(
//                model = stock.logoUrl,
//                contentDescription = stock.companyName,
//                modifier = Modifier.size(48.dp).clip(CircleShape)
//            )
//            Spacer(modifier = Modifier.width(12.dp))
//            Column {
//                Text(stock.companyName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
//                Text(stock.ticker, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Price
//        Text(
//            "$${"%.2f".format(stock.currentPrice)}",
//            style = MaterialTheme.typography.headlineMedium,
//            fontWeight = FontWeight.Bold
//        )
//        Text(
//            "${if(stock.isPositiveChange) "+" else "-"}${"%.2f".format(stock.priceChange)} (${ "%.2f".format(stock.percentChange)}%)",
//            color = if(stock.isPositiveChange) Color.Green else Color.Red,
//            style = MaterialTheme.typography.bodyMedium
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Day info
//        InfoRow("Open", "$${"%.2f".format(stock.openPrice)}")
//        InfoRow("High", "$${"%.2f".format(stock.highPrice)}")
//        InfoRow("Low", "$${"%.2f".format(stock.lowPrice)}")
//        InfoRow("Previous Close", "$${"%.2f".format(stock.previousClosePrice)}")
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Additional info
//        stock.website?.let { InfoRow("Website", it) }
//        stock.industry?.let { InfoRow("Industry", it) }
//        stock.exchange?.let { InfoRow("Exchange", it) }
//        stock.country?.let { InfoRow("Country", it) }
//        stock.currency?.let { InfoRow("Currency", it) }
//    }
//}
//
@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
    }
}
