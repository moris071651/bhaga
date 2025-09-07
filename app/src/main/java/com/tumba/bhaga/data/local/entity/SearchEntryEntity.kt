package com.tumba.bhaga.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tumba.bhaga.domain.models.SearchEntry

@Entity(tableName = "search_entry")
data class SearchEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "ticker") val ticker: String,
    @ColumnInfo(name = "companyName") val companyName: String,
    @ColumnInfo(name = "logo_url") val logoUrl: String
)

fun SearchEntryEntity.toSearchEntry(): SearchEntry {
    return SearchEntry(
        ticker = this.ticker,
        companyName = this.companyName,
        logoUrl = this.logoUrl
    )
}
