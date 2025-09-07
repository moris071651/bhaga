package com.tumba.bhaga.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface StockDao {
    @Transaction
    @Query("SELECT * FROM company_profile WHERE ticker = :ticker")
    suspend fun getCompanyWithQuoteAndNews(ticker: String): CompanyWithQuoteAndNews?

    @Transaction
    @Query("SELECT * FROM company_profile WHERE ticker = :ticker")
    suspend fun getCompanyWithQuote(ticker: String): CompanyWithQuote?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyProfile(profile: CompanyProfileEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: QuoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<CompanyNewsEntity>)

    @Query("DELETE FROM company_news WHERE ticker = :ticker")
    suspend fun clearNewsForTicker(ticker: String)

    @Query("SELECT * FROM company_news WHERE ticker = :ticker ORDER BY publishedAt DESC")
    suspend fun getNewsForCompany(ticker: String): List<CompanyNewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavourite(favourite: FavouriteEntity)

    @Query("DELETE FROM favourite_company WHERE ticker = :ticker")
    suspend fun removeFavourite(ticker: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favourite_company WHERE ticker = :ticker)")
    suspend fun isFavourite(ticker: String): Boolean

    @Transaction
    @Query("""
        SELECT cp.*, q.* FROM favourite_company f
        INNER JOIN company_profile cp ON f.ticker = cp.ticker
        LEFT JOIN company_quote q ON cp.ticker = q.ticker
        ORDER BY f.addedAt DESC
    """)
    suspend fun getFavouriteCompanies(): List<CompanyWithQuote>

    @Query("UPDATE company_quote SET lastUpdated = 0")
    suspend fun invalidateAllQuotes()

    @Query("UPDATE company_profile SET lastUpdated = 0")
    suspend fun invalidateAllCompanyProfiles()

    @Query("UPDATE company_news SET lastUpdated = 0")
    suspend fun invalidateAllNews()

    @Query("SELECT * FROM search_entry")
    suspend fun getAllSearchEntries(): List<SearchEntryEntity>

    @Query("SELECT * FROM search_entry WHERE ticker = :ticker LIMIT 1")
    suspend fun findSearchEntryByTicker(ticker: String): SearchEntryEntity?
}
