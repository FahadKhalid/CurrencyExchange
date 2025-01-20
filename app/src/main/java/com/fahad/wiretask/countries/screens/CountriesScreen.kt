package com.fahad.wiretask.countries.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.androidtest.countries.model.CountriesResponse
import com.fahad.wiretask.countries.viewmodel.CountriesViewModel
import com.fahad.wiretask.utils.isInternetAvailable
import com.example.androidtest.countries.presentation.states.CountriesUiState
import com.fahad.wiretask.utils.LoadingView
import com.fahad.wiretask.utils.loadCountriesFromAssets
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json

@Composable
fun CountriesScreen(
    @ApplicationContext context: Context,
    viewModel: CountriesViewModel = hiltViewModel(),
    onCountryClick: (String) -> Unit
) {

    // NOTE: The API often returns STREAM ERROR, so for development and
    // testing purposes, i am using a local JSON file (`countries.json`)
    // instead of making actual network requests to the API. This ensures
    // that the app remains functional while debugging or testing.
    // FetchDataFromAPI(viewModel, context, onCountryClick)

    //The app currently loads country data from the assets folder using loadJsonFromAssets,
    // as the API response was unreliable. You can uncomment the API code to test it if needed.
    FetchDataFromAssets(context, onCountryClick)
}

@Composable
private fun FetchDataFromAPI(
    viewModel: CountriesViewModel,
    context: Context,
    onCountryClick: (String) -> Unit
) {
    val uiState: CountriesUiState by viewModel.uiState.collectAsStateWithLifecycle()
    // Render UI based on state
    when (val state = uiState) {
        is CountriesUiState.Loading -> LoadingView()
        is CountriesUiState.Success -> {
            LazyColumn {
                items(state.countriesResponse) { country ->
                    CountryCard(
                        country = country,
                        context = context,
                        onCountryClick
                    )
                }
            }
        }

        is CountriesUiState.Failed -> {
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
private fun FetchDataFromAssets(
    context: Context,
    onCountryClick: (String) -> Unit
) {
    val items = loadCountriesFromAssets(context, "countries.json")
    if (items != null) {
        ItemListScreen(items, context, onCountryClick)
    }
}

@Composable
fun CountryCard(
    country: CountriesResponse,
    context: Context,
    onCountryClick: (String) -> Unit
) {
    val jsonString = Json.encodeToString(CountriesResponse.serializer(), country)
    val isConnected = isInternetAvailable()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                if (isConnected) {
                    onCountryClick(jsonString)
                } else {
                    Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()

                }
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Country Flag
            AsyncImage(
                model = country.flags.png,
                contentDescription = "${country.name.common} Flag",
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))

            // Country Information
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = country.name.common,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                country.currencies?.values?.firstOrNull()?.let {
                    Text(
                        text = "${it.name} (${it.symbol})",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            // Forward Arrow Icon
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Navigate to Details",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview
@Composable
fun CountryCardPreview(
) {
    CountryCard(
        CountriesResponse.previewData,
        LocalContext.current
    ) {}
}

// ============================ NOTE ============================
// This code is used as a fallback when the countries API is not
// working or unavailable. It loads data from a local JSON file
// for development and testing purposes.
// ==============================================================
@Composable
fun ItemListScreen(
    items: List<CountriesResponse>,
    context: Context,
    onCountryClick: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            CountryCard(country = item, context, onCountryClick)
        }
    }
}