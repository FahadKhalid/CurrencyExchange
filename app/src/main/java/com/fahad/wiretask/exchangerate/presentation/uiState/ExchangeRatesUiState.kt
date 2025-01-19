package com.fahad.wiretask.exchangerate.presentation.uiState

sealed interface ExchangeRatesUiState {
    data object Loading : ExchangeRatesUiState

    data class Success(
        val exchangeRateResponse: Double,
    ) : ExchangeRatesUiState

    data class Failed(
        val message: String,
    ) : ExchangeRatesUiState
}