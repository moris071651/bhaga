package com.tumba.bhaga.ui.screens.stockdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.tumba.bhaga.ui.components.CompanyTag
import com.tumba.bhaga.ui.components.PercentChangeBadge
import com.tumba.bhaga.ui.components.StockNewsList
import kotlin.math.abs

@Composable
fun StockDetailScreen(
    ticker: String,
    viewModel: StockDetailViewModel = hiltViewModel()
) {
    val stock by viewModel.stock.collectAsState()
    val isFavourite by viewModel.isFavourite.collectAsState()

    LaunchedEffect(ticker) {
        viewModel.loadStock(ticker)
    }

    if (stock == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    else {
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CompanyTag(
                            stock!!.ticker,
                            stock!!.companyName,
                            stock!!.logoUrl,
                            Modifier.fillMaxHeight()
                        )

                        Spacer(Modifier.weight(1f))

                        IconButton(
                            onClick = {
                                if (isFavourite) viewModel.removeFromFavourites()
                                else viewModel.addToFavourites()
                            }
                        ) {
                            Icon(
                                imageVector = if (isFavourite) Icons.Filled.Star else Icons.Outlined.Star,
                                contentDescription = if (isFavourite) "Remove from favourites" else "Add to favourites",
                                tint = if (isFavourite) Color.Yellow else Color.Gray
                            )
                        }
                    }
                }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column {
                            Text(
                                "$${"%.2f".format(stock!!.currentPrice)}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(Modifier.height(4.dp))

                            Text(
                                "${if (stock!!.isPositiveChange) "+" else "-"}${
                                    " $%.2f".format(
                                        abs(
                                            stock!!.priceChange
                                        )
                                    )
                                }",
                                color = Color(when (stock!!.isPositiveChange) {
                                    true -> ColorUtils.blendARGB(
                                        Color.Green.toArgb(),
                                        Color.Black.toArgb(),
                                        0.4f
                                    )
                                    false -> ColorUtils.blendARGB(
                                        Color.Red.toArgb(),
                                        Color.Black.toArgb(),
                                        0.2f
                                    )
                                }),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        PercentChangeBadge(
                            stock!!.percentChange,
                            stock!!.isPositiveChange,
                            Modifier.padding(8.dp)
                        )
                    }
                }


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

            StockNewsList(stock!!.newsList, modifier = Modifier.padding(vertical = 0.dp))
        }
    }
}

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
