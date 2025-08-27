package com.tumba.bhaga.data.di

import androidx.room.Room
import com.tumba.bhaga.BhagaApp
import com.tumba.bhaga.data.local.StockDatabase
import com.tumba.bhaga.data.remote.FinnhubApi
import com.tumba.bhaga.data.remote.StockRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object AppModule {
    val db: StockDatabase by lazy {
        Room.databaseBuilder(
            BhagaApp.instance,
            StockDatabase::class.java,
            "stock_db"
        ).build()
    }

    val httpClient: HttpClient by lazy {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    val api: FinnhubApi by lazy {
        FinnhubApi(httpClient, "d2n8qthr01qn3vmk0gr0d2n8qthr01qn3vmk0grg")
    }

    val repository: StockRepository by lazy {
        StockRepository(api, db)
    }
}
