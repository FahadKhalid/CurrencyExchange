package com.fahad.wiretask.exchangerate.data.repo

import com.fahad.wiretask.exchangerate.data.api.ExchangeRateApi
import javax.inject.Inject

class ExchangeRateRepositoryImpl @Inject constructor(
    private val exchangeRateApi: ExchangeRateApi
) : ExchangeRateRepository {

    override suspend fun getExchangeRate(currencyCode: String): Double {
        val response = exchangeRateApi.getCurrencyRates(currencyCode)
        return if (response.isSuccessful && response.body() != null) {
            response.body()?.rates?.get("USD") ?: 0.0
        } else {
            throw Exception("Failed to fetch rates: ${response.message()}")
        }
    }
}
