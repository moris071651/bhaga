package com.tumba.bhaga.data.remote.dto

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
