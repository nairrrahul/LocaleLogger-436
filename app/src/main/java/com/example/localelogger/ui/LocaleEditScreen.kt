package com.example.localelogger.ui

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.localelogger.R

@Composable
fun LocaleEditScreen(navController: NavController, localeId: Int, viewModel: LocaleEditViewModel = viewModel(
    factory = LocaleEditViewModel.Factory(
        LocalContext.current.applicationContext as Application,
        localeId)
)) {
    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    val selectedIcon by viewModel.selectedIcon.collectAsState()

    Scaffold(bottomBar = {
        BottomAppBar {
            Button(onClick = { navController.navigate("home") }) {
                Text("Home")
            }
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Edit Locale", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            if (selectedIcon.isNotEmpty()) {
                Icon(
                    painter = painterResource(id = getIconResId(selectedIcon)),
                    contentDescription = "Locale Icon",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            OutlinedTextField(value = title, onValueChange = viewModel::updateTitle, label = { Text("Title") })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = description, onValueChange = viewModel::updateDescription, label = { Text("Description") })
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                viewModel.saveLocale()
                navController.navigate("locale_view")
            }) {
                Text("Save")
            }
        }
    }
}

@Composable
fun getIconResId(iconType: String): Int {
    return when (iconType) {
        "ocean" -> R.drawable.ocean
        "plains" -> R.drawable.plains
        "mountain" -> R.drawable.mountain
        "forest" -> R.drawable.forest
        "city" -> R.drawable.city
        "rainforest" -> R.drawable.rainforest
        "beach" -> R.drawable.beach
        "cave" -> R.drawable.cave
        else -> R.drawable.placeholder
    }
}
