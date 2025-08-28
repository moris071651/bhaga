package com.tumba.bhaga.data.remote

import com.tumba.bhaga.data.local.StockDatabase
import com.tumba.bhaga.data.local.StockEntity
import com.tumba.bhaga.data.local.toStockSummary
import com.tumba.bhaga.domain.models.StockSummary
import androidx.room.withTransaction
import com.tumba.bhaga.data.local.toStockDetail
import com.tumba.bhaga.domain.models.StockDetail
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StockRepository(
    private val api: FinnhubApi,
    private val db: StockDatabase,
    private val cacheTimeoutMillis: Long = 86_400_000 // 1 day
) {

    private val dao = db.stockDao()

    suspend fun getStockSummary(ticker: String): StockSummary {
        return withContext(Dispatchers.IO) {
            val cached = dao.getStock(ticker)
            val now = System.currentTimeMillis()

            if (cached != null && now - cached.lastUpdated < cacheTimeoutMillis) {
                cached.toStockSummary()
            }
            else {
                val profile = api.getCompanyProfile(ticker)
                val quote = api.getQuote(ticker)

                val entity = StockEntity(
                    ticker = ticker,
                    companyName = profile.companyName ?: ticker,
                    logoUrl = profile.logoUrl,
                    website = profile.website,
                    industry = profile.industry,
                    exchange = profile.exchange,
                    country = profile.country,
                    currency = profile.currency,
                    currentPrice = quote.currentPrice,
                    highPrice = quote.highPrice,
                    lowPrice = quote.lowPrice,
                    openPrice = quote.openPrice,
                    previousClosePrice = quote.previousClosePrice,
                    priceChange = quote.priceChange,
                    percentChange = quote.percentChange,
                    lastUpdated = now
                )

                db.withTransaction {
                    dao.insertStock(entity)
                }

                entity.toStockSummary()
            }
        }
    }

    suspend fun getStockDetail(ticker: String): StockDetail {
        return withContext(Dispatchers.IO) {
            val cached = dao.getStock(ticker)
            val now = System.currentTimeMillis()

            if (cached != null && now - cached.lastUpdated < cacheTimeoutMillis) {
                cached.toStockDetail()
            }
            else {
                val profile = api.getCompanyProfile(ticker)
                val quote = api.getQuote(ticker)

                val entity = StockEntity(
                    ticker = ticker,
                    companyName = profile.companyName ?: ticker,
                    logoUrl = profile.logoUrl,
                    website = profile.website,
                    industry = profile.industry,
                    exchange = profile.exchange,
                    country = profile.country,
                    currency = profile.currency,
                    currentPrice = quote.currentPrice,
                    highPrice = quote.highPrice,
                    lowPrice = quote.lowPrice,
                    openPrice = quote.openPrice,
                    previousClosePrice = quote.previousClosePrice,
                    priceChange = quote.priceChange,
                    percentChange = quote.percentChange,
                    lastUpdated = now
                )

                db.withTransaction {
                    dao.insertStock(entity)
                }

                entity.toStockDetail()
            }
        }
    }
}
