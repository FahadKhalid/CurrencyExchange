package com.fahad.wiretask.exchangerate.presentation.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.serialization.json.Json
import com.example.androidtest.countries.model.CountriesResponse
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import coil3.compose.AsyncImage
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fahad.wiretask.exchangerate.presentation.viewmodel.ExchangeRateViewModel
import com.fahad.wiretask.exchangerate.presentation.uiState.ExchangeRatesUiState
import com.fahad.wiretask.utils.LoadingView

@Composable
fun ExchangeRateScreen(
    country: String,
    context: Context,
    viewModel: ExchangeRateViewModel = hiltViewModel(),
) {
    val uiState: ExchangeRatesUiState by viewModel.uiState.collectAsStateWithLifecycle()

    val jsonString = Uri.decode(country)
    val countryData = remember(jsonString) {
        jsonString?.let {
            Json.decodeFromString(CountriesResponse.serializer(), it)
        }
    }

    LaunchedEffect(Unit) {
        countryData?.currencies?.keys?.firstOrNull()?.let { currencyCode ->
            viewModel.fetchExchangeRate(currencyCode)
        }
    }

    // Render UI based on state
    when (val state = uiState) {
        is ExchangeRatesUiState.Loading -> LoadingView()
        is ExchangeRatesUiState.Success -> {
            if (countryData != null) {
                ExchangeRateCard(
                    countryData = countryData,
                    exchangeRate = state.exchangeRateResponse
                )
            }
        }

        is ExchangeRatesUiState.Failed -> {
            LaunchedEffect(state.message) {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            Text(
                text = state.message,
                modifier = Modifier.padding(16.dp),
                color = Color.Red
            )
        }
    }
}

@Composable
fun ExchangeRateCard(
    countryData: CountriesResponse,
    exchangeRate: Double,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.9f)
                .background(Color.White)
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Flag Image
                AsyncImage(
                    model = countryData.flags.png,
                    contentDescription = "${countryData.name.common} Flag",
                    modifier = Modifier
                        .size(120.dp)
                        .border(
                            2.dp,
                            Color.Gray,
                            RoundedCornerShape(8.dp)
                        ),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Country Name
                Text(
                    text = countryData.name.common,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Currency Details
                Text(
                    text = "Currency: ${countryData.currencies?.values?.firstOrNull()?.name ?: "N/A"}",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Text(
                    text = "Currency Symbol: ${countryData.currencies?.values?.firstOrNull()?.symbol ?: "N/A"}",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Exchange Rate
                Text(
                    text = "Exchange Rate to USD: $exchangeRate",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
@Preview
fun ExchangeRateCardPreview() {
    ExchangeRateCard(CountriesResponse.previewData, 1.0)
}