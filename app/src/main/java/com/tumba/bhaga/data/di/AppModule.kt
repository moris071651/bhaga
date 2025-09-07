package com.tumba.bhaga.data.di

import androidx.room.Room
import com.tumba.bhaga.BhagaApp
import com.tumba.bhaga.data.local.StockDatabase
import com.tumba.bhaga.data.local.TokenManager
import com.tumba.bhaga.data.remote.FinnhubApi
import com.tumba.bhaga.data.remote.StockRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object AppModule {
    val db: StockDatabase by lazy {
        StockDatabase.getInstance(BhagaApp.instance)
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

    val tokenManager: TokenManager by lazy {
        TokenManager(BhagaApp.instance)
    }

    val api: FinnhubApi by lazy {
        FinnhubApi(httpClient, tokenManager)
    }

    val repository: StockRepository by lazy {
        StockRepository(api, db)
    }
}
