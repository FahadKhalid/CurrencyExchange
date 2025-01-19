package com.fahad.wiretask.countries.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahad.wiretask.countries.domain.GetCountriesUseCase
import com.example.androidtest.countries.presentation.states.CountriesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<CountriesUiState>(CountriesUiState.Loading)
    val uiState: StateFlow<CountriesUiState> = _uiState.asStateFlow()

    init {
        fetchCountries()
    }

    fun fetchCountries() {
        viewModelScope.launch {
            _uiState.value = CountriesUiState.Loading
            try {
                val getCountriesUseCase = getCountriesUseCase()
                _uiState.value = getCountriesUseCase.body()?.let { CountriesUiState.Success(it) }!!
            } catch (e: Exception) {
                _uiState.value = CountriesUiState.Failed("Error: ${e.localizedMessage}")
            }
        }
    }
}