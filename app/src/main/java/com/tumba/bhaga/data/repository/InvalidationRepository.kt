package com.tumba.bhaga.data.repository

import com.tumba.bhaga.data.local.StockDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InvalidationRepository(private val db: StockDatabase) {
    suspend fun invalidateAllStockData() = withContext(Dispatchers.IO) {
        val dao = db.invalidationDao()
        dao.invalidateAllQuotes()
    }

    suspend fun invalidateAllCompanyData() = withContext(Dispatchers.IO) {
        val dao = db.invalidationDao()
        dao.invalidateAllCompanyProfiles()
    }

    suspend fun invalidateAllNewsData() = withContext(Dispatchers.IO) {
        val dao = db.invalidationDao()
        dao.invalidateAllNews()
    }
}
