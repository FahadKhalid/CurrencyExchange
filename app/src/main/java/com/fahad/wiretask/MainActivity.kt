package com.fahad.wiretask

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.fahad.wiretask.navigation.NavigationHost
import com.fahad.wiretask.theme.AndroidTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    @ApplicationContext
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidTheme {
                App(context)
            }
        }
    }
}

@Composable
private fun App(
    @ApplicationContext context: Context,
) {
    val navController = rememberNavController()
    Scaffold {
        NavigationHost(
            context = context,
            modifier = Modifier.padding(it),
            navController = navController,
        )
    }
}
