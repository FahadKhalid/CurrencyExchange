package com.fahad.wiretask.exchangerate.data.api

import com.fahad.wiretask.exchangerate.model.ExchangeRateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRateApi {
    // feel free to change the return type as you see fit as long as the body [ExchangeRateResponse]
    // this api should use "https://api.exchangerate-api.com/v4/latest/{currencyCode}"
    // e.g. https://api.exchangerate-api.com/v4/latest/USD

    @GET("v4/latest/{currencyCode}")
    suspend fun getCurrencyRates(
        @Path("currencyCode") currencyCode: String
    ): Response<ExchangeRateResponse>
}