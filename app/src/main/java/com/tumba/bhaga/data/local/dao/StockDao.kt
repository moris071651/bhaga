package com.tumba.bhaga.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.tumba.bhaga.data.local.entity.CompanyProfileEntity
import com.tumba.bhaga.data.local.entity.CompanyWithQuote
import com.tumba.bhaga.data.local.entity.CompanyWithQuoteAndNews
import com.tumba.bhaga.data.local.entity.QuoteEntity

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
}
