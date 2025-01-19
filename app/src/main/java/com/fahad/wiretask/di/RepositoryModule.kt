package com.fahad.wiretask.di

import com.fahad.wiretask.exchangerate.data.api.ExchangeRateApi
import com.fahad.wiretask.exchangerate.data.repo.ExchangeRateRepository
import com.fahad.wiretask.exchangerate.data.repo.ExchangeRateRepositoryImpl
import com.fahad.wiretask.exchangerate.domain.usecases.GetExchangeRateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExchangeRateModule {

    @Provides
    @Singleton
    fun provideExchangeRateRepository(
        exchangeRateApi: ExchangeRateApi
    ): ExchangeRateRepository {
        return ExchangeRateRepositoryImpl(exchangeRateApi)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetExchangeRateUseCase(
        exchangeRateRepository: ExchangeRateRepository
    ): GetExchangeRateUseCase {
        return GetExchangeRateUseCase(exchangeRateRepository)
    }
}