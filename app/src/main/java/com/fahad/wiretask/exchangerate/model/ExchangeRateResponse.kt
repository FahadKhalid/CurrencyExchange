package com.fahad.wiretask.exchangerate.model

import com.example.androidtest.countries.model.CurrencyCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ExchangeRateResponse(
    @SerialName("rates")
    val rates: Map<CurrencyCode, Double>
)