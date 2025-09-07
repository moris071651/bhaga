package com.tumba.bhaga.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.tumba.bhaga.domain.models.StockDetail
import com.tumba.bhaga.domain.models.StockNews
import com.tumba.bhaga.domain.models.StockSummary

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
