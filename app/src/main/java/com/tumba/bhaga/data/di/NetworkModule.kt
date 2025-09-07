package com.tumba.bhaga.data.di

import android.content.Context
import com.tumba.bhaga.data.local.TokenManager
import com.tumba.bhaga.data.remote.FinnhubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

    @Provides
    @Singleton
    fun provideFinnhubApi(
        client: HttpClient,
        tokenManager: TokenManager
    ): FinnhubApi = FinnhubApi(client, tokenManager)
}
