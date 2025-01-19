package com.fahad.wiretask.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fahad.wiretask.countries.screens.CountriesScreen
import com.fahad.wiretask.exchangerate.presentation.screens.ExchangeRateScreen
import com.fahad.wiretask.navigation.AppDestinations.EXCHANGE_RATE
import com.fahad.wiretask.navigation.AppDestinationsArgs.COUNTRY_ARGS
import dagger.hilt.android.qualifiers.ApplicationContext

@Composable
fun NavigationHost(
    @ApplicationContext context: Context,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screens.CountriesScreen.name,
    navActions: NavigationActions = remember(navController) {
        NavigationActions(navController)
    },
) {
    NavHost(navController = navController, startDestination = startDestination, modifier) {
        composable(Screens.CountriesScreen.name) {
            CountriesScreen(
                context,
                onCountryClick = navActions::navigateToDetails
            )
        }

        composable(
            EXCHANGE_RATE,
            arguments = listOf(navArgument(COUNTRY_ARGS) {
                type = StringType
            })
        ) {
            val currencyCode = it.arguments?.getString(COUNTRY_ARGS)
            if (currencyCode != null) {
                ExchangeRateScreen(country = currencyCode,context)
            }
        }
    }
}