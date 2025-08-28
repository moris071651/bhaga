package com.tumba.bhaga.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface StockDao {
    // -------------------------
    // Company Profile + Quote + News
    // -------------------------

    // Fetch a company with its latest quote and all news (for detail screen)
    @Transaction
    @Query("SELECT * FROM company_profile WHERE ticker = :ticker")
    suspend fun getCompanyWithQuoteAndNews(ticker: String): CompanyWithQuoteAndNews?

    // Fetch a company with its latest quote only (for favorites list)
    @Transaction
    @Query("SELECT * FROM company_profile WHERE ticker = :ticker")
    suspend fun getCompanyWithQuote(ticker: String): CompanyWithQuote?

    // Insert or update company profile
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyProfile(profile: CompanyProfileEntity)

    // Insert or update quote
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: QuoteEntity)

    // Insert or update news articles
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<CompanyNewsEntity>)

    // Clear old news for a company
    @Query("DELETE FROM company_news WHERE ticker = :ticker")
    suspend fun clearNewsForTicker(ticker: String)

    // Get all news for a company, newest first
    @Query("SELECT * FROM company_news WHERE ticker = :ticker ORDER BY publishedAt DESC")
    suspend fun getNewsForCompany(ticker: String): List<CompanyNewsEntity>

    // -------------------------
    // Favorites
    // -------------------------

    // Add a company to favorites
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavourite(favourite: FavouriteEntity)

    // Remove a company from favorites
    @Query("DELETE FROM favourite_company WHERE ticker = :ticker")
    suspend fun removeFavourite(ticker: String)

    // Check if a company is in favorites
    @Query("SELECT EXISTS(SELECT 1 FROM favourite_company WHERE ticker = :ticker)")
    suspend fun isFavourite(ticker: String): Boolean

    // Get all favorites with profile + quote (for watchlist)
    @Transaction
    @Query("""
        SELECT cp.*, q.* FROM favourite_company f
        INNER JOIN company_profile cp ON f.ticker = cp.ticker
        LEFT JOIN company_quote q ON cp.ticker = q.ticker
        ORDER BY f.addedAt DESC
    """)
    suspend fun getFavouriteCompanies(): List<CompanyWithQuote>
}

