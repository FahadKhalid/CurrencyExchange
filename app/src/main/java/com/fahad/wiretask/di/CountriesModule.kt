package com.fahad.wiretask.di

import com.example.androidtest.countries.data.api.CountriesApi
import com.example.androidtest.countries.data.repo.CountriesRepository
import com.fahad.wiretask.countries.CountriesRepositoryImpl
import com.fahad.wiretask.countries.domain.GetCountriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CountriesModule {

    @Provides
    @Singleton
    fun provideCountriesRepository(
        countriesApi: CountriesApi
    ): CountriesRepository {
        return CountriesRepositoryImpl(countriesApi)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
object CountriesUseCaseModule {

    @Provides
    fun provideGetExchangeRateUseCase(
        countriesRepository: CountriesRepository
    ): GetCountriesUseCase {
        return GetCountriesUseCase(countriesRepository)
    }
}