package com.tumba.bhaga.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
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
