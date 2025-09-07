package com.tumba.bhaga.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchEntryDTO(
    @SerialName("symbol") val ticker: String,
    @SerialName("description") val companyName: String
)
