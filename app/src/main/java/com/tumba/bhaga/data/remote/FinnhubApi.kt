package com.tumba.bhaga.data.remote

import com.tumba.bhaga.data.local.TokenManager
import com.tumba.bhaga.data.remote.dto.CompanyNewsDto
import com.tumba.bhaga.data.remote.dto.CompanyProfileDto
import com.tumba.bhaga.data.remote.dto.QuoteDto
import com.tumba.bhaga.data.remote.dto.SearchEntryDTO
import com.tumba.bhaga.domain.models.SearchEntry
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.LocalDate

class FinnhubApi(
    private val client: HttpClient,
    private val tokenManager: TokenManager
) {
    private val BASE = "https://finnhub.io/api/v1"

    suspend fun getQuote(ticker: String): QuoteDto {
        return client.get("$BASE/quote") {
            parameter("symbol", ticker)
            parameter("token", tokenManager.getToken())
        }.body()
    }

    suspend fun getCompanyProfile(ticker: String): CompanyProfileDto {
        return client.get("$BASE/stock/profile2") {
            parameter("symbol", ticker)
            parameter("token", tokenManager.getToken())
        }.body()
    }

    suspend fun getCompanyNews(ticker: String): List<CompanyNewsDto> {
        val today = LocalDate.now()
        val dayAgo = today.minusDays(1)

        return client.get("$BASE/company-news") {
            parameter("symbol", ticker)
            parameter("from", dayAgo.toString())
            parameter("to", today.toString())
            parameter("token", tokenManager.getToken())
        }.body<List<CompanyNewsDto>>().take(10)
    }

    suspend fun checkTokenStatus(token: String): Int {
        return client.get("$BASE/quote") {
            parameter("symbol", "AAPL")
            parameter("token", token)
        }.status.value
    }
}
