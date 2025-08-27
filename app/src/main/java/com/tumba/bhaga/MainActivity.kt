package com.tumba.bhaga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tumba.bhaga.domain.models.StockSummary
import com.tumba.bhaga.ui.components.StockSummaryList
import com.tumba.bhaga.ui.theme.BhagaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val stocks = (1..60).map { num ->
            StockSummary(
                ticker = "TSLA${num}",
                companyName = "Tesla, Inc.Tesla, Inc.Tesla, Inc.Tesla, Inc.Tesla, Inc.Tesla, Inc.Tesla, Inc.Tesla, Inc.Tesla, Inc.Tesla, Inc.",
                logoUrl = "https://logo.clearbit.com/amazon.com",
                currentPrice = 235.67,
                priceChange = 8.0,
                percentChange = 2.35,
                isPositiveChange = true
            )
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BhagaTheme {
                StockSummaryList(stocks, Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BhagaTheme {
        Greeting("Android")
    }
}