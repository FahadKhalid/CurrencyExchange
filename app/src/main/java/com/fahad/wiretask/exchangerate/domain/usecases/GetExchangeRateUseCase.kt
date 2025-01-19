package com.fahad.wiretask.exchangerate.domain.usecases

import com.fahad.wiretask.exchangerate.data.repo.ExchangeRateRepository
import javax.inject.Inject

class GetExchangeRateUseCase @Inject constructor(
    private val exchangeRateRepository: ExchangeRateRepository
) {

    suspend operator fun invoke(currencyCode: String): Double {
        return exchangeRateRepository.getExchangeRate(currencyCode)
    }
}
