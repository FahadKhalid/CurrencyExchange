package com.fahad.wiretask.countries.domain

import com.example.androidtest.countries.data.repo.CountriesRepository
import com.example.androidtest.countries.model.CountriesResponse
import retrofit2.Response
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val countriesRepository: CountriesRepository
) {

    suspend operator fun invoke(): Response<List<CountriesResponse>> {
        return countriesRepository.fetchCountriesFullList()
    }
}
