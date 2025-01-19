package com.fahad.wiretask.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.androidtest.countries.data.api.CountriesApi
import com.fahad.wiretask.exchangerate.data.api.ExchangeRateApi
import com.fahad.wiretask.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCountriesApi(): CountriesApi {
        return ApiService().countriesApi
    }

    @Provides
    @Singleton
    fun provideExchangeApi(): ExchangeRateApi {
        return ApiService().exchangeRateApi
    }

    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}
