package com.tumba.bhaga.data.repository

import androidx.room.withTransaction
import com.tumba.bhaga.data.local.StockDatabase
import com.tumba.bhaga.domain.models.StockSummary
import com.tumba.bhaga.data.local.entity.CompanyNewsEntity
import com.tumba.bhaga.data.local.entity.CompanyProfileEntity
import com.tumba.bhaga.data.local.entity.QuoteEntity
import com.tumba.bhaga.data.local.entity.toStockDetail
import com.tumba.bhaga.data.local.entity.toStockSummary
import com.tumba.bhaga.data.remote.FinnhubApi
import com.tumba.bhaga.domain.models.StockDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StockRepository(
    private val api: FinnhubApi,
    private val db: StockDatabase,
    private val quoteCacheMillis: Long = 24 * 60 * 60_000,   // 15 min for quotes
    private val profileCacheMillis: Long = 14 * 24 * 60 * 60_000, // 1 day for profile
    private val newsCacheMillis: Long = 24 * 60 * 60_000     // 1 day for news
) {
    suspend fun getStockSummary(ticker: String): StockSummary = withContext(Dispatchers.IO) {
        val dao = db.stockDao()

        val now = System.currentTimeMillis()
        var company = dao.getCompanyWithQuote(ticker)

        val profileStale =
            company?.profile?.let { now - it.lastUpdated > profileCacheMillis } ?: true
        val quoteStale = company?.quote?.let { now - it.lastUpdated > quoteCacheMillis } ?: true

        if (profileStale || quoteStale) {
            db.withTransaction {
                if (profileStale) fetchAndSaveProfile(ticker, now)
                if (quoteStale) fetchAndSaveQuote(ticker, now)
            }
        }

        company = dao.getCompanyWithQuote(ticker)
        company!!.toStockSummary()
    }

    suspend fun getStockDetail(ticker: String): StockDetail = withContext(Dispatchers.IO) {
        val dao = db.stockDao()

        val now = System.currentTimeMillis()

        val companyWithQuoteAndNews = dao.getCompanyWithQuoteAndNews(ticker)
        val profileStale =
            companyWithQuoteAndNews?.profile?.let { now - it.lastUpdated > profileCacheMillis }
                ?: true
        val quoteStale =
            companyWithQuoteAndNews?.quote?.let { now - it.lastUpdated > quoteCacheMillis } ?: true
        val newsStale = companyWithQuoteAndNews?.news?.firstOrNull()
            ?.let { now - it.lastUpdated > newsCacheMillis } ?: true

        if (profileStale) fetchAndSaveProfile(ticker, now)
        if (quoteStale) fetchAndSaveQuote(ticker, now)
        if (newsStale) fetchAndSaveNews(ticker, now)

        dao.getCompanyWithQuoteAndNews(ticker)!!.toStockDetail()
    }

    private suspend fun fetchAndSaveProfile(ticker: String, now: Long) {
        val dao = db.stockDao()

        val profile = api.getCompanyProfile(ticker)
        val entity = CompanyProfileEntity(
            ticker = ticker,
            companyName = profile.companyName ?: ticker,
            logoUrl = profile.logoUrl,
            website = profile.website,
            industry = profile.industry,
            exchange = profile.exchange,
            country = profile.country,
            currency = profile.currency,
            lastUpdated = now
        )

        dao.insertCompanyProfile(entity)
    }

    private suspend fun fetchAndSaveQuote(ticker: String, now: Long) {
        val dao = db.stockDao()

        val quote = api.getQuote(ticker)
        val entity = QuoteEntity(
            ticker = ticker,
            currentPrice = quote.currentPrice,
            highPrice = quote.highPrice,
            lowPrice = quote.lowPrice,
            openPrice = quote.openPrice,
            previousClosePrice = quote.previousClosePrice,
            priceChange = quote.priceChange,
            percentChange = quote.percentChange,
            lastUpdated = now
        )

        dao.insertQuote(entity)
    }

    private suspend fun fetchAndSaveNews(ticker: String, now: Long) {
        val dao = db.newsDao()

        val newsList = api.getCompanyNews(ticker)
        val entities = newsList.map {
            CompanyNewsEntity(
                ticker = ticker,
                title = it.title,
                summary = it.summary,
                imageUrl = it.imageUrl,
                link = it.link,
                publishedAt = it.publishedAt,
                lastUpdated = now
            )
        }

        dao.clearNewsForTicker(ticker)
        dao.insertNews(entities)
    }
}
