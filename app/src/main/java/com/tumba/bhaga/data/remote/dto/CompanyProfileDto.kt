package com.tumba.bhaga.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
