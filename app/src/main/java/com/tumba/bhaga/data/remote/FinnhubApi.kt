package com.tumba.bhaga.data.remote

import com.tumba.bhaga.data.remote.dto.CompanyProfileDto
import com.tumba.bhaga.data.remote.dto.QuoteDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class FinnhubApi(
    private val client: HttpClient,
    private val apiKey: String
) {
    suspend fun getQuote(ticker: String): QuoteDto {
        return client.get("https://finnhub.io/api/v1/quote") {
            parameter("symbol", ticker)
            parameter("token", apiKey)
        }.body()
    }

    suspend fun getCompanyProfile(ticker: String): CompanyProfileDto {
        return client.get("https://finnhub.io/api/v1/stock/profile2") {
            parameter("symbol", ticker)
            parameter("token", apiKey)
        }.body()
    }
}
