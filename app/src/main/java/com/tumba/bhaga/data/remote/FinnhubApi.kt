package com.tumba.bhaga.data.remote

import com.tumba.bhaga.data.remote.dto.CompanyNewsDto
import com.tumba.bhaga.data.remote.dto.CompanyProfileDto
import com.tumba.bhaga.data.remote.dto.QuoteDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.LocalDate

class FinnhubApi(
    private val client: HttpClient,
    private val apiKey: String
) {

    private val API_PREFIX = "https://finnhub.io/api/v1"
    suspend fun getQuote(ticker: String): QuoteDto {
        return client.get("$API_PREFIX/quote") {
            parameter("symbol", ticker)
            parameter("token", apiKey)
        }.body()
    }

    suspend fun getCompanyProfile(ticker: String): CompanyProfileDto {
        return client.get("$API_PREFIX/stock/profile2") {
            parameter("symbol", ticker)
            parameter("token", apiKey)
        }.body()
    }

    suspend fun getCompanyNews(ticker: String): List<CompanyNewsDto> {
        val today = LocalDate.now()
        val dayAgo = today.minusDays(1)

        return client.get("$API_PREFIX/company-news") {
            parameter("symbol", ticker)
            parameter("from", dayAgo.toString())
            parameter("to", today.toString())
            parameter("token", apiKey)
        }.body<List<CompanyNewsDto>>().take(6)
    }
}
