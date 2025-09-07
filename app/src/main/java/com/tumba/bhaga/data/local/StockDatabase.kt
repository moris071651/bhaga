package com.tumba.bhaga.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tumba.bhaga.data.local.dao.FavouritesDao
import com.tumba.bhaga.data.local.dao.InvalidationDao
import com.tumba.bhaga.data.local.dao.NewsDao
import com.tumba.bhaga.data.local.dao.SearchDao
import com.tumba.bhaga.data.local.dao.StockDao
import com.tumba.bhaga.data.local.entity.CompanyNewsEntity
import com.tumba.bhaga.data.local.entity.CompanyProfileEntity
import com.tumba.bhaga.data.local.entity.FavouriteEntity
import com.tumba.bhaga.data.local.entity.QuoteEntity
import com.tumba.bhaga.data.local.entity.SearchEntryEntity
import java.io.FileOutputStream

@Database(
    entities = [
        CompanyProfileEntity::class,
        QuoteEntity::class,
        CompanyNewsEntity::class,
        FavouriteEntity::class,
        SearchEntryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class StockDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDao

    abstract fun favouritesDao(): FavouritesDao

    abstract fun invalidationDao(): InvalidationDao

    abstract fun newsDao(): NewsDao

    abstract fun searchDao(): SearchDao

    companion object {
        @Volatile private var INSTANCE: StockDatabase? = null

        fun getInstance(context: Context): StockDatabase {
            return INSTANCE ?: synchronized(this) {
                val dbName = "search_entry.db"
                val dbPath = context.getDatabasePath(dbName)

                if (!dbPath.exists()) {
                    dbPath.parentFile?.mkdirs()
                    context.assets.open(dbName).use { input ->
                        FileOutputStream(dbPath).use { output ->
                            input.copyTo(output)
                        }
                    }
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StockDatabase::class.java,
                    dbName
                )
                    .createFromFile(dbPath)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
