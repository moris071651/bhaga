package com.tumba.bhaga.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tumba.bhaga.data.local.entity.CompanyNewsEntity

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<CompanyNewsEntity>)

    @Query("DELETE FROM company_news WHERE ticker = :ticker")
    suspend fun clearNewsForTicker(ticker: String)

    @Query("SELECT * FROM company_news WHERE ticker = :ticker ORDER BY publishedAt DESC")
    suspend fun getNewsForCompany(ticker: String): List<CompanyNewsEntity>
}
