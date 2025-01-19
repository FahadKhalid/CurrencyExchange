package com.fahad.wiretask.exchangerate.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahad.wiretask.exchangerate.presentation.uiState.ExchangeRatesUiState
import com.fahad.wiretask.exchangerate.domain.usecases.GetExchangeRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeRateViewModel @Inject constructor(
    private val getExchangeRateUseCase: GetExchangeRateUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<ExchangeRatesUiState>(ExchangeRatesUiState.Loading)
    val uiState: StateFlow<ExchangeRatesUiState> = _uiState.asStateFlow()

    fun fetchExchangeRate(currencyCode: String) {
        viewModelScope.launch {
            _uiState.value = ExchangeRatesUiState.Loading
            try {
                val usdRate = getExchangeRateUseCase(currencyCode)
                _uiState.value = ExchangeRatesUiState.Success(usdRate)
            } catch (e: Exception) {
                _uiState.value = ExchangeRatesUiState.Failed("Error: ${e.localizedMessage}")
            }
        }
    }
}
