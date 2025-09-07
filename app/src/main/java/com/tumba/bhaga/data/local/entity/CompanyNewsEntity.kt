package com.tumba.bhaga.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "company_news",
    foreignKeys = [
        ForeignKey(
            entity = CompanyProfileEntity::class,
            parentColumns = ["ticker"],
            childColumns = ["ticker"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("ticker")]
)
data class CompanyNewsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val ticker: String,
    val title: String,
    val summary: String?,
    val imageUrl: String,
    val link: String,
    val publishedAt: Long,
    val lastUpdated: Long
)
