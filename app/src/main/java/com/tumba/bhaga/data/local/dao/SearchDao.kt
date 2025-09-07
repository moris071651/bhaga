package com.tumba.bhaga.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.tumba.bhaga.data.local.entity.SearchEntryEntity

@Dao
interface SearchDao {
    @Query("SELECT * FROM search_entry")
    suspend fun getAllSearchEntries(): List<SearchEntryEntity>

    @Query("SELECT * FROM search_entry WHERE ticker = :ticker LIMIT 1")
    suspend fun findSearchEntryByTicker(ticker: String): SearchEntryEntity?
}
