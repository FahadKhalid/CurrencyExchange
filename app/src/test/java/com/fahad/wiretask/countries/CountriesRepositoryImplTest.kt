package com.fahad.wiretask.countries

import com.example.androidtest.countries.model.CountriesResponse
import com.example.androidtest.countries.model.CountriesResponse.CountryName
import com.example.androidtest.countries.model.CountriesResponse.Currency
import com.example.androidtest.countries.model.CountriesResponse.Flag
import com.example.androidtest.countries.presentation.states.CountriesUiState
import com.fahad.wiretask.countries.domain.GetCountriesUseCase
import com.fahad.wiretask.countries.viewmodel.CountriesViewModel
import org.junit.Assert.*
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.setMain
import kotlin.test.assertTrue

class CountriesRepositoryImplTest {

    private lateinit var getCountriesUseCase: GetCountriesUseCase
    private lateinit var countriesViewModel: CountriesViewModel

    // Use StandardTestDispatcher instead of TestCoroutineDispatcher
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Set the main dispatcher to the test dispatcher
        Dispatchers.setMain(testDispatcher)

        // Mock the use case
        getCountriesUseCase = mockk()
        countriesViewModel = CountriesViewModel(getCountriesUseCase)
    }

    @Test
    fun `fetchCountries emits Success when UseCase returns countries`() = runTest {
        // Arrange: Mock UseCase to return a successful response
        val mockCountries = listOf(
            CountriesResponse(
                name = CountryName("Germany"),
                currencies = mapOf(
                    "EUR" to Currency(
                        name = "Euro",
                        symbol = "€"
                    )
                ),
                flags = Flag(
                    png = "https://upload.wikimedia.org/wikipedia/en/b/ba/Flag_of_Germany.svg",
                    svg = "https://upload.wikimedia.org/wikipedia/en/b/ba/Flag_of_Germany.svg"
                )
            )
        )

        // Mock the response from UseCase to return the mock data
        coEvery { getCountriesUseCase() } returns Response.success(mockCountries)

        // Act: Call the ViewModel's fetchCountries method
        countriesViewModel.fetchCountries()

        // Wait for the state to be emitted (if necessary, with advanceTimeBy)
        advanceUntilIdle()  // This will ensure any coroutine tasks are completed

        // Assert: Check that the emitted UI state is Success and matches the expected data
        val uiState = countriesViewModel.uiState.first()
        assertTrue(uiState is CountriesUiState.Success, "Expected Success state but got: $uiState")
        assertEquals(mockCountries, (uiState as CountriesUiState.Success).countriesResponse)
    }
}