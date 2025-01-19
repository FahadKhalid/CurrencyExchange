package com.fahad.wiretask.exchangerate.data.repo

import com.fahad.wiretask.exchangerate.data.api.ExchangeRateApi
import com.fahad.wiretask.exchangerate.model.ExchangeRateResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.fail
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ExchangeRateRepositoryImplTest {

    @MockK
    private lateinit var exchangeRateRepositoryImpl: ExchangeRateRepositoryImpl

    @MockK
    private lateinit var exchangeRateApi: ExchangeRateApi

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        exchangeRateRepositoryImpl = ExchangeRateRepositoryImpl(exchangeRateApi)
    }

    @Test
    fun `getExchangeRate returns USD rate when API call succeeds`() = runTest {
        // Arrange: Mock API response
        val mockResponse = ExchangeRateResponse(rates = mapOf("USD" to 1.2))
        coEvery { exchangeRateApi.getCurrencyRates("USD") } returns Response.success(mockResponse)

        // Act: Call the repository method
        val repository = ExchangeRateRepositoryImpl(exchangeRateApi)
        val usdRate = repository.getExchangeRate("USD")

        // Assert: Check the returned rate
        assertThat(usdRate).isEqualTo(1.2)
    }

    @Test
    fun `getExchangeRate throws IllegalArgumentException when currencyCode is empty`() = runTest {
        // Arrange: Mock the behavior for an empty currency code
        coEvery { exchangeRateApi.getCurrencyRates("") } throws IllegalArgumentException("Currency code cannot be empty")

        // Act & Assert: Call the repository method with an empty string and expect an exception
        val repository = ExchangeRateRepositoryImpl(exchangeRateApi)

        try {
            repository.getExchangeRate("")  // Pass an empty string
            // Fail the test if no exception is thrown
            fail("Expected IllegalArgumentException to be thrown")
        } catch (e: IllegalArgumentException) {
            // Assert: Check the exception message
            assertThat(e.message).isEqualTo("Currency code cannot be empty")
        }
    }

}