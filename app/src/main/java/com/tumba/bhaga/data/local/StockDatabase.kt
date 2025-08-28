package com.tumba.bhaga.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        CompanyProfileEntity::class,
        QuoteEntity::class,
        CompanyNewsEntity::class,
        FavouriteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class StockDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao
}
