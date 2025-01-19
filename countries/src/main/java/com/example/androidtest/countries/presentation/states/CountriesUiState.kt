package com.example.androidtest.countries.presentation.states

import com.example.androidtest.countries.model.CountriesResponse

sealed interface CountriesUiState {
    object Loading : CountriesUiState

    data class Success(
        val countriesResponse: List<CountriesResponse>,
    ) : CountriesUiState

    data class Failed(
        val message: String,
    ) : CountriesUiState
}