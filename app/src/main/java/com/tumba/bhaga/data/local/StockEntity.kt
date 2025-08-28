package com.tumba.bhaga.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.tumba.bhaga.domain.models.StockDetail
import com.tumba.bhaga.domain.models.StockNews
import com.tumba.bhaga.domain.models.StockSummary

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

@Entity(
    tableName = "company_quote",
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
data class QuoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val ticker: String,
    val currentPrice: Double,
    val highPrice: Double,
    val lowPrice: Double,
    val openPrice: Double,
    val previousClosePrice: Double,
    val priceChange: Double,
    val percentChange: Double,
    val lastUpdated: Long
)

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

// when i fetch favourites i what to return List<CompanyWithQuote>

data class CompanyWithQuote(
    @Embedded val profile: CompanyProfileEntity,

    @Relation(
        parentColumn = "ticker",
        entityColumn = "ticker"
    )
    val quote: QuoteEntity?
)

data class CompanyWithQuoteAndNews(
    @Embedded val profile: CompanyProfileEntity,

    @Relation(
        parentColumn = "ticker",
        entityColumn = "ticker"
    )
    val quote: QuoteEntity?,

    @Relation(
        parentColumn = "ticker",
        entityColumn = "ticker"
    )
    val news: List<CompanyNewsEntity>
)

fun CompanyWithQuote.toStockSummary(): StockSummary {
    val q = quote
    return StockSummary(
        ticker = profile.ticker,
        companyName = profile.companyName,
        logoUrl = profile.logoUrl,
        currentPrice = q?.currentPrice ?: 0.0,
        priceChange = q?.priceChange ?: 0.0,
        percentChange = q?.percentChange ?: 0.0,
        isPositiveChange = (q?.priceChange ?: 0.0) >= 0
    )
}

fun CompanyWithQuoteAndNews.toStockSummary(): StockSummary {
    val q = quote
    return StockSummary(
        ticker = profile.ticker,
        companyName = profile.companyName,
        logoUrl = profile.logoUrl,
        currentPrice = q?.currentPrice ?: 0.0,
        priceChange = q?.priceChange ?: 0.0,
        percentChange = q?.percentChange ?: 0.0,
        isPositiveChange = (q?.priceChange ?: 0.0) >= 0
    )
}

fun CompanyWithQuoteAndNews.toStockDetail(): StockDetail {
    val q = quote
    return StockDetail(
        ticker = profile.ticker,
        companyName = profile.companyName,
        logoUrl = profile.logoUrl,
        currentPrice = q?.currentPrice ?: 0.0,
        priceChange = q?.priceChange ?: 0.0,
        percentChange = q?.percentChange ?: 0.0,
        isPositiveChange = (q?.priceChange ?: 0.0) >= 0,
        website = profile.website,
        industry = profile.industry,
        exchange = profile.exchange,
        country = profile.country,
        currency = profile.currency,
        highPrice = q?.highPrice ?: 0.0,
        lowPrice = q?.lowPrice ?: 0.0,
        openPrice = q?.openPrice ?: 0.0,
        previousClosePrice = q?.previousClosePrice ?: 0.0,
        newsList = news.map {
            StockNews(
                title = it.title,
                summary = it.summary,
                imageUrl = it.imageUrl,
                link = it.link,
                publishedAt = it.publishedAt,
            )
        }
    )
}
// check if everything is fine
