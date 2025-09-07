package com.tumba.bhaga.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favourite_company",
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
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val ticker: String,
    val addedAt: Long = System.currentTimeMillis()
)
