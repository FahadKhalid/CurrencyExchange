package com.fahad.wiretask.exchangerate.presentation.uiState

sealed interface ExchangeRatesUiState {
    object Loading : ExchangeRatesUiState

    data class Success(
        val exchangeRateResponse: Double,
    ) : ExchangeRatesUiState

    data class Failed(
        val message: String,
    ) : ExchangeRatesUiState
}