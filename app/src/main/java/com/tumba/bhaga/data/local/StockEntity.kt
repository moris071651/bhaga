package com.tumba.bhaga.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tumba.bhaga.domain.models.StockSummary

@Entity(tableName = "stocks")
data class StockEntity(
    @PrimaryKey val ticker: String,

    // --- Company profile ---
    val companyName: String,
    val logoUrl: String?,
    val website: String?,
    val industry: String?,
    val exchange: String?,
    val country: String?,
    val currency: String?,

    // --- Quote info ---
    val currentPrice: Double,
    val highPrice: Double,
    val lowPrice: Double,
    val openPrice: Double,
    val previousClosePrice: Double,
    val priceChange: Double,
    val percentChange: Double,

    // --- Metadata ---
    val lastUpdated: Long
)

fun StockEntity.toStockSummary(): StockSummary {
    return StockSummary(
        ticker = ticker,
        companyName = companyName,
        logoUrl = logoUrl,
        currentPrice = currentPrice,
        priceChange = priceChange,
        percentChange = percentChange,
        isPositiveChange = priceChange >= 0
    )
}