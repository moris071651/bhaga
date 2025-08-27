package com.tumba.bhaga.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuoteDto(
    @SerialName("c") val currentPrice: Double,
    @SerialName("h") val highPrice: Double,
    @SerialName("l") val lowPrice: Double,
    @SerialName("o") val openPrice: Double,
    @SerialName("pc") val previousClosePrice: Double,
    @SerialName("d") val priceChange: Double,
    @SerialName("dp") val percentChange: Double
)

@Serializable
data class CompanyProfileDto(
    @SerialName("ticker") val ticker: String,
    @SerialName("name") val companyName: String? = null,
    @SerialName("logo") val logoUrl: String? = null,
    @SerialName("weburl") val website: String? = null,
    @SerialName("finnhubIndustry") val industry: String? = null,
    @SerialName("exchange") val exchange: String? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("currency") val currency: String? = null
)
