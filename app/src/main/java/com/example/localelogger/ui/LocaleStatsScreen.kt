package com.example.localelogger.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.localelogger.R

@Composable
fun LocaleStatsScreen(navController: NavController, viewModel: LocaleStatsViewModel = viewModel()) {

    val isNetCount by viewModel.isNetCount.collectAsState()
    val netCounts by viewModel.netCounts.collectAsState()
    val monthCounts by viewModel.monthCounts.collectAsState()

    Scaffold(
        bottomBar = {
            BottomAppBar {
                Button(onClick = { navController.navigate("home") }) {
                    Text("Home")
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { viewModel.toggleState() }) {
                    Text(if (isNetCount) "Change to Month Count" else "Change to Net Count")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = if (isNetCount) "Net Locale Count" else "Month-by-Month Count",
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (isNetCount) {
                netCounts.forEach { count ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = getIconResource(count.iconType)),
                            contentDescription = count.iconType,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(count.iconType, modifier = Modifier.width(100.dp))
                        LinearProgressIndicator(
                            progress = {
                                count.count / 10f
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(20.dp),
                        )
                        Text(" ${count.count}")
                    }
                }
            } else {
                monthCounts.forEach { count ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Text(count.monthYear, modifier = Modifier.width(100.dp))
                        LinearProgressIndicator(
                            progress = { count.count / 10f },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(20.dp),
                        )
                        Text(" ${count.count}")
                    }
                }
            }
        }
    }
}

@Composable
fun getIconResource(iconType: String): Int {
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