package com.example.localelogger.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LocaleAddScreen(navController: NavController, fusedLocationClient: FusedLocationProviderClient, viewModel: LocaleAddViewModel = viewModel()) {
    LaunchedEffect(Unit) { viewModel.fetchLocation(fusedLocationClient) }

    val selectedIcon by viewModel.selectedIcon.collectAsState()
    val title by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    val location by viewModel.location.collectAsState()
    val timestamp by viewModel.timestamp.collectAsState()

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
            Text("Add a Locale", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            val icons = listOf("ocean", "plains", "mountain", "forest", "city", "rainforest", "beach", "cave")
            Column {
                icons.chunked(4).forEach { rowIcons ->
                    Row {
                        rowIcons.forEach { icon ->
                            Image(
                                painter = painterResource(id = getDrawableId(icon)),
                                contentDescription = icon,
                                modifier = Modifier
                                    .size(64.dp)
                                    .padding(4.dp)
                                    .clickable { viewModel.selectIcon(icon) }
                                    .then(if(icon == selectedIcon) {
                                        Modifier.border(2.dp, Color.Blue)
                                    } else {
                                        Modifier
                                    })
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text=selectedIcon,  fontSize = 20.sp, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = title, onValueChange = viewModel::updateTitle, label = { Text("Title") })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = description, onValueChange = viewModel::updateDescription, label = { Text("Description") })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = location, onValueChange = {}, label = { Text("Location") }, enabled = false)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = timestamp, onValueChange = {}, label = { Text("Timestamp") }, enabled = false)

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.saveLocale() }) {
                Text("Save")
            }
        }
    }
}

@Composable
fun getDrawableId(iconName: String): Int {
    val context = LocalContext.current
    return context.resources.getIdentifier(iconName, "drawable", context.packageName)
}
