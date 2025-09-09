package com.tumba.bhaga.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
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
)
data class FavouriteEntity(
    @PrimaryKey val ticker: String,
    val addedAt: Long = System.currentTimeMillis()
)
