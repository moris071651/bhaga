package com.tumba.bhaga.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {

    @Query("SELECT * FROM stocks WHERE ticker = :ticker")
    suspend fun getStock(ticker: String): StockEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(stock: StockEntity)

    @Query("SELECT * FROM stocks ORDER BY percentChange DESC LIMIT :limit")
    suspend fun getTopStocks(limit: Int): List<StockEntity>
}
