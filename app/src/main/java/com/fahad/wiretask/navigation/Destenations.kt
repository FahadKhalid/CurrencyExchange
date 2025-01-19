package com.fahad.wiretask.navigation

import androidx.navigation.NavController
import com.fahad.wiretask.navigation.AppDestinationsArgs.COUNTRY_ARGS
import com.fahad.wiretask.navigation.AppScreens.EXCHANGE_RATE_SCREEN
import android.net.Uri

object AppDestinationsArgs {
    const val COUNTRY_ARGS = "Country"
}

private object AppScreens {
    const val EXCHANGE_RATE_SCREEN = "EXCHANGE_RATE_SCREEN"
}

object AppDestinations {
    const val EXCHANGE_RATE =
        "$EXCHANGE_RATE_SCREEN/{$COUNTRY_ARGS}"
}

enum class Screens {
    CountriesScreen,
}

class NavigationActions(private val navController: NavController) {
    fun navigateToDetails(
        country: String,
    ) {
        val encodedJson = Uri.encode(country)
        navController.navigate("$EXCHANGE_RATE_SCREEN/$encodedJson")
    }
}