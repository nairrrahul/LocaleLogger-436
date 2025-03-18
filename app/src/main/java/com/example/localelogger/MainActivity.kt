package com.example.localelogger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.localelogger.ui.HomeScreen
import com.example.localelogger.ui.LocaleAddScreen
import com.example.localelogger.ui.LocaleEditScreen
import com.example.localelogger.ui.LocaleStatsScreen
import com.example.localelogger.ui.LocaleViewerScreen
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            LocaleLoggerApp(navController, fusedLocationClient)
        }
    }
}

@Composable
fun LocaleLoggerApp(navController: NavHostController, fusedLocationClient: com.google.android.gms.location.FusedLocationProviderClient) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("locale_add") { LocaleAddScreen(navController, fusedLocationClient) }
        composable("locale_view") { LocaleViewerScreen(navController) }
        composable("locale_edit/{localeId}") { backStackEntry ->
            val localeId = backStackEntry.arguments?.getString("localeId")?.toIntOrNull() ?: 0
            LocaleEditScreen(navController, localeId)
        }
        composable("locale_stats") { LocaleStatsScreen(navController) }
    }
}