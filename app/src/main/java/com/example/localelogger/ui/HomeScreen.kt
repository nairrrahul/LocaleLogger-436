package com.example.localelogger.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("LocaleLogger", style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate("locale_add") }) {
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add a Locale")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate("locale_view") }) {
            Spacer(modifier = Modifier.width(8.dp))
            Text("View Saved Locales")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate("locale_stats") }) {
            Spacer(modifier = Modifier.width(8.dp))
            Text("View Locale Stats")
        }
    }
}