package com.tumba.bhaga.data.local.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface InvalidationDao {
    @Query("UPDATE company_quote SET lastUpdated = 0")
    suspend fun invalidateAllQuotes()

    @Query("UPDATE company_profile SET lastUpdated = 0")
    suspend fun invalidateAllCompanyProfiles()

    @Query("UPDATE company_news SET lastUpdated = 0")
    suspend fun invalidateAllNews()
}
