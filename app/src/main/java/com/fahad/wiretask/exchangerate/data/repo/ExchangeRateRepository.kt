package com.fahad.wiretask.exchangerate.data.repo

interface ExchangeRateRepository {
    suspend fun getExchangeRate(currencyCode: String): Double
}