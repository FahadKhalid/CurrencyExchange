package com.fahad.wiretask.utils

import android.content.Context
import com.example.androidtest.countries.model.CountriesResponse
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

fun loadJsonFromAssets(context: Context, fileName: String): List<CountriesResponse> {
    val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    return json.decodeFromString(jsonString)
}
