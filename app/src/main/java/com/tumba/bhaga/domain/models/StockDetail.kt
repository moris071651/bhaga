package com.tumba.bhaga.domain.models

data class StockDetail(
    val ticker: String,
    val companyName: String,
    val logoUrl: String?,
    val website: String?,
    val industry: String?,
    val exchange: String?,
    val country: String?,
    val currency: String?,
    val currentPrice: Double,
    val highPrice: Double,
    val lowPrice: Double,
    val openPrice: Double,
    val previousClosePrice: Double,
    val priceChange: Double,
    val percentChange: Double,
    val isPositiveChange: Boolean,
    val newsList: List<StockNews>
)
