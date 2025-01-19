package com.example.androidtest.countries.data.repo

import com.example.androidtest.countries.model.CountriesResponse
import retrofit2.Response

interface CountriesRepository {
    suspend fun fetchCountriesFullList(): Response<List<CountriesResponse>>
}