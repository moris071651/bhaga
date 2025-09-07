package com.tumba.bhaga.data.repository

import com.tumba.bhaga.data.local.StockDatabase
import com.tumba.bhaga.data.local.entity.toSearchEntry
import com.tumba.bhaga.domain.models.SearchEntry

class SearchRepository(private val db: StockDatabase) {
    suspend fun getAllSearchEntries(): List<SearchEntry> {
        val dao = db.searchDao()
        return dao.getAllSearchEntries().map {
            it.toSearchEntry()
        }
    }
}
