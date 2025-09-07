package com.tumba.bhaga.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.tumba.bhaga.domain.models.StockSummary

data class CompanyWithQuote(
    @Embedded val profile: CompanyProfileEntity,

    @Relation(
        parentColumn = "ticker",
        entityColumn = "ticker"
    )
    val quote: QuoteEntity?
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
