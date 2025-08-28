package com.tumba.bhaga.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyNewsDto(
    @SerialName("headline") val title: String,
    @SerialName("image") val imageUrl: String,
    @SerialName("summary") val summary: String,
    @SerialName("url") val link: String,
    @SerialName("datetime") val publishedAt: Long,
)
