package com.example.androidtest.countries.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias CurrencyCode = String

@Serializable
class CountriesResponse(
    @SerialName("name")
    val name: CountryName = CountryName(""),
    @SerialName("currencies")
    val currencies: Map<CurrencyCode, Currency>? = null,
    @SerialName("flags")
    val flags: Flag = Flag("", "")
) {
    companion object {
        val previewData: CountriesResponse =
            CountriesResponse(
                name = CountryName("Germany"),
                currencies = mapOf(
                    "EUR" to Currency(
                        name = "Euro",
                        symbol = "€"
                    )
                ),
                flags = Flag(
                    png = "https://upload.wikimedia.org/wikipedia/en/b/ba/Flag_of_Germany.svg",
                    svg = "https://upload.wikimedia.org/wikipedia/en/b/ba/Flag_of_Germany.svg"
                )

            )
    }

    @Serializable
    class Flag(
        @SerialName("png")
        val png: String,
        @SerialName("svg")
        val svg: String
    )

    @Serializable
    class Currency(
        @SerialName("name")
        val name: String,
        @SerialName("symbol")
        val symbol: String
    )

    @Serializable
    class CountryName(
        @SerialName("common")
        val common: String
    )
}