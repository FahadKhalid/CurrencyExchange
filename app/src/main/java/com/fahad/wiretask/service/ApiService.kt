package com.fahad.wiretask.service

import com.example.androidtest.countries.data.api.CountriesApi
import com.fahad.wiretask.exchangerate.data.api.ExchangeRateApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

class ApiService {
    @OptIn(ExperimentalSerializationApi::class)
    val json by lazy {
        Json {
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = true
            explicitNulls = false
            coerceInputValues = true
        }
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    }
    private val contentType = "application/json; charset=utf-8".toMediaType()

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    private val countriesRetrofit by lazy {
        createRetrofit("https://restcountries.com/v3.1/")
    }

    private val currencyExchangeRetrofit by lazy {
        createRetrofit("https://api.exchangerate-api.com/")
    }

    // Create API interfaces
    val countriesApi: CountriesApi by lazy {
        countriesRetrofit.create(CountriesApi::class.java)
    }

    val exchangeRateApi: ExchangeRateApi by lazy {
        currencyExchangeRetrofit.create(ExchangeRateApi::class.java)
    }
}