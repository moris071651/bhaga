package com.tumba.bhaga.data.repository

import com.tumba.bhaga.data.local.StockDatabase
import com.tumba.bhaga.data.local.entity.FavouriteEntity
import com.tumba.bhaga.data.local.entity.toStockSummary
import com.tumba.bhaga.domain.models.StockSummary

class FavouritesRepository(private val db: StockDatabase) {
    suspend fun addFavourite(ticker: String) {
        val dao = db.favouritesDao()
        return dao.addFavourite(FavouriteEntity(ticker = ticker))
    }

    suspend fun removeFavourite(ticker: String) {
        val dao = db.favouritesDao()
        return dao.removeFavourite(ticker)
    }

    suspend fun getFavouriteCompanies(): List<StockSummary> {
        val dao = db.favouritesDao()
        return dao.getFavouriteCompanies().map {
            it.toStockSummary()
        }
    }

    suspend fun checkFavourite(ticker: String): Boolean {
        val dao = db.favouritesDao()
        return dao.isFavourite(ticker)
    }
}