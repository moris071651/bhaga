package com.tumba.bhaga.data.remote

import androidx.room.withTransaction
import com.tumba.bhaga.data.local.StockDatabase
import com.tumba.bhaga.data.local.toStockSummary
import com.tumba.bhaga.domain.models.StockSummary
import com.tumba.bhaga.data.local.CompanyNewsEntity
import com.tumba.bhaga.data.local.CompanyProfileEntity
import com.tumba.bhaga.data.local.FavouriteEntity
import com.tumba.bhaga.data.local.QuoteEntity
import com.tumba.bhaga.data.local.toStockDetail
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

    private val dao = db.stockDao()

    // -----------------------------
    // Get stock summary (profile + quote)
    // -----------------------------
    suspend fun getStockSummary(ticker: String): StockSummary = withContext(Dispatchers.IO) {
        val now = System.currentTimeMillis()
        var company = dao.getCompanyWithQuote(ticker)

        val profileStale = company?.profile?.let { now - it.lastUpdated > profileCacheMillis } ?: true
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

    // -----------------------------
    // Get stock detail (profile + quote + news)
    // -----------------------------
    suspend fun getStockDetail(ticker: String): StockDetail = withContext(Dispatchers.IO) {
        val now = System.currentTimeMillis()

        val companyWithQuoteAndNews = dao.getCompanyWithQuoteAndNews(ticker)
        val quoteStale = companyWithQuoteAndNews?.quote?.let { now - it.lastUpdated > quoteCacheMillis } ?: true
        val newsStale = companyWithQuoteAndNews?.news?.firstOrNull()?.let { now - it.lastUpdated > newsCacheMillis } ?: true
        val profileStale = companyWithQuoteAndNews?.profile?.let { now - it.lastUpdated > profileCacheMillis } ?: true

        if (quoteStale) fetchAndSaveQuote(ticker, now)
        if (profileStale) fetchAndSaveProfile(ticker, now)
        if (newsStale) fetchAndSaveNews(ticker, now)

        dao.getCompanyWithQuoteAndNews(ticker)!!.toStockDetail()
    }

    // -----------------------------
    // Fetch & save profile
    // -----------------------------
    private suspend fun fetchAndSaveProfile(ticker: String, now: Long) {
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

    // -----------------------------
    // Fetch & save quote
    // -----------------------------
    private suspend fun fetchAndSaveQuote(ticker: String, now: Long) {
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

    // -----------------------------
    // Fetch & save news
    // -----------------------------
    private suspend fun fetchAndSaveNews(ticker: String, now: Long) {
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

    // -----------------------------
    // Favorites
    // -----------------------------
    suspend fun addFavourite(ticker: String) = dao.addFavourite(FavouriteEntity(ticker = ticker))
    suspend fun removeFavourite(ticker: String) = dao.removeFavourite(ticker)
    suspend fun getFavouriteCompanies(): List<StockSummary> =
        dao.getFavouriteCompanies().map { it.toStockSummary() }
}
