package com.fahad.wiretask.exchangerate.model

import com.example.androidtest.countries.model.CurrencyCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ExchangeRateResponse(
    @SerialName("provider")
    val provider: String,
    @SerialName("terms")
    val terms: String,
    @SerialName("base")
    val base: CurrencyCode,
    @SerialName("date")
    val date: String,
    @SerialName("time_last_updated")
    val timeLastUpdated: Long,
    @SerialName("rates")
    val rates: Map<CurrencyCode, Double>
)