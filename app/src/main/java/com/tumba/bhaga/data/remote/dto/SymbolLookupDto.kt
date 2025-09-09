package com.tumba.bhaga.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object for the Finnhub symbol search API.
 */
@Serializable
data class SymbolLookupDto(
    @SerialName("count")
    val count: Int,
    @SerialName("result")
    val result: List<SymbolLookupItemDto>
)

@Serializable
data class SymbolLookupItemDto(
    @SerialName("description")
    val description: String,
    @SerialName("displaySymbol")
    val displaySymbol: String,
    @SerialName("symbol")
    val symbol: String,
    @SerialName("type")
    val type: String
)
