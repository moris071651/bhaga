package com.tumba.bhaga.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.tumba.bhaga.data.local.entity.CompanyWithQuote
import com.tumba.bhaga.data.local.entity.FavouriteEntity

@Dao
interface FavouritesDao {
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
}
