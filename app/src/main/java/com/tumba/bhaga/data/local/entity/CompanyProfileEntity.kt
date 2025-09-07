package com.tumba.bhaga.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company_profile")
data class CompanyProfileEntity(
    @PrimaryKey val ticker: String,
    val companyName: String,
    val logoUrl: String?,
    val website: String?,
    val industry: String?,
    val exchange: String?,
    val country: String?,
    val currency: String?,
    val lastUpdated: Long
)
