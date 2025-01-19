package com.fahad.wiretask.countries

import com.example.androidtest.countries.data.api.CountriesApi
import com.example.androidtest.countries.data.repo.CountriesRepository
import com.example.androidtest.countries.model.CountriesResponse
import retrofit2.Response
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(
    private val countriesApi: CountriesApi
) : CountriesRepository {

    override suspend fun fetchCountriesFullList(): Response<List<CountriesResponse>> {
        val response = countriesApi.fetchCountriesFullList()
        return if (response.isSuccessful) {
            response
        } else {
            throw Exception("Failed to fetch rates: ${response.message()}")
        }
    }
}