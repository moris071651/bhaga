package com.tumba.bhaga.domain.models

data class StockSummary(
    val ticker: String,
    val companyName: String,
    val logoUrl: String?,
    val currentPrice: Double,
    val priceChange: Double,
    val percentChange: Double,
    val isPositiveChange: Boolean,
)
