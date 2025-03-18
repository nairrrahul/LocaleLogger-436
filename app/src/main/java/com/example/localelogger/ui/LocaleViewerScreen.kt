package com.example.localelogger.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.localelogger.data.LocaleEntity
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource


@Composable
fun LocaleViewerScreen(navController: NavController, viewModel: LocaleViewerViewModel = viewModel()) {
    val localesGrouped by viewModel.localesGrouped.collectAsState()
    var selectedLocale by remember { mutableStateOf<LocaleEntity?>(null) }

    Scaffold(bottomBar = {
        BottomAppBar {
            Button(onClick = { navController.navigate("home") }) {
                Text("Home")
            }
        }
    }) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            localesGrouped.forEach { (month, locales) ->
                item {
                    Text(month, style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(locales) { locale ->
                    Row(modifier = Modifier.fillMaxWidth().clickable { selectedLocale = locale }) {
                        Image(
                            painter = painterResource(id = getDrawableIdForViewer(locale.iconType)),
                            contentDescription = locale.iconType,
                            modifier = Modifier.size(64.dp).padding(4.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(locale.title, style = MaterialTheme.typography.bodyLarge)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    selectedLocale?.let { locale ->
        AlertDialog(
            onDismissRequest = { selectedLocale = null },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { selectedLocale = null }) {
                    Text("X")
                }
            },
            title = { Text(locale.title) },
            text = {
                Column {
                    Image(
                        painter = painterResource(id = getDrawableIdForViewer(locale.iconType)),
                        contentDescription = locale.iconType,
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(locale.description)
                    Text("Location: ${locale.location}")
                    Text("Timestamp: ${locale.timestamp}")
                    Button(onClick = {navController.navigate("locale_edit/${locale.id}")}) {
                        Text("Edit", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        )
    }
}

@Composable
fun getDrawableIdForViewer(iconName: String): Int {
    val context = LocalContext.current
    return context.resources.getIdentifier(iconName, "drawable", context.packageName)
}