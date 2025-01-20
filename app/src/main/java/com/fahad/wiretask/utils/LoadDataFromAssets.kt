package com.fahad.wiretask.utils

import android.content.Context
import com.example.androidtest.countries.model.CountriesResponse
import com.google.gson.Gson
import java.io.IOException

fun loadCountriesFromAssets(context: Context, fileName: String): List<CountriesResponse>? {
    return try {
        // Load the JSON as a string
        val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        // Deserialize JSON into a list of CountriesResponse
        Gson().fromJson(jsonString, Array<CountriesResponse>::class.java).toList()
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}