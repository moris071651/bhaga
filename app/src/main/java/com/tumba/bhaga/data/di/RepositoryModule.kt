package com.tumba.bhaga.data.di

import com.tumba.bhaga.data.local.StockDatabase
import com.tumba.bhaga.data.remote.FinnhubApi
import com.tumba.bhaga.data.remote.StockRepository
import com.tumba.bhaga.data.repository.FavouritesRepository
import com.tumba.bhaga.data.repository.InvalidationRepository
import com.tumba.bhaga.data.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideStockRepository(
        api: FinnhubApi,
        db: StockDatabase
    ): StockRepository = StockRepository(api, db)

    @Provides
    @Singleton
    fun provideFavouritesRepository(
        db: StockDatabase
    ): FavouritesRepository = FavouritesRepository(db)

    @Provides
    @Singleton
    fun provideSearchRepository(
        db: StockDatabase
    ): SearchRepository = SearchRepository(db)

    @Provides
    @Singleton
    fun provideInvalidationRepository(
        db: StockDatabase
    ): InvalidationRepository = InvalidationRepository(db)
}
