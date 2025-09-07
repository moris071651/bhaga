package com.tumba.bhaga.data.di

import android.content.Context
import com.tumba.bhaga.data.local.TokenManager
import com.tumba.bhaga.data.remote.FinnhubApi
import com.tumba.bhaga.data.local.TokenValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TokenModule {

    @Provides
    @Singleton
    fun provideTokenManager(
        @ApplicationContext context: Context
    ): TokenManager = TokenManager(context)

    fun provideTokenValidator(
        api: FinnhubApi
    ): TokenValidator = TokenValidator(api)
}