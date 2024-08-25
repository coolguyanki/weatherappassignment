package com.example.weatherappassignment.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherappassignment.NetworkResponse
import com.example.weatherappassignment.R
import com.example.weatherappassignment.WeatherViewModel
import com.example.weatherappassignment.data.WeatherApiResponse
import com.example.weatherappassignment.navigation.WeatherScreens
import com.example.weatherappassignment.ui.theme.GradientBrush

@Composable
fun WeatherScreen(navController: NavHostController, weatherViewModel: WeatherViewModel = viewModel()) {
    var city by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weather") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(WeatherScreens.Onboarding.route) {
                            popUpTo(WeatherScreens.Onboarding.route) { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = city,
                        singleLine = true,
                        onValueChange = { city = it },
                        label = { Text(text = "Search for any location...") }
                    )

                    IconButton(onClick = {
                        weatherViewModel.getData(12.9082847623315, 77.65197822993314)
                    }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search city")
                    }
                }
                val weatherResponse by weatherViewModel.weatherData.observeAsState()

                when (weatherResponse) {
                    is NetworkResponse.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is NetworkResponse.Success -> {
                        val weatherData = (weatherResponse as NetworkResponse.Success<WeatherApiResponse>).data
                        Log.e("Ankit", "Error message: $weatherData")

                        WeatherDataDisplay(weatherData)
                    }
                    is NetworkResponse.Error -> {
                        val errorMessage = (weatherResponse as NetworkResponse.Error).message
                        Log.e("WeatherApp", "Error message: $errorMessage")
                        Text(text = errorMessage, color = MaterialTheme.colors.error)
                    }

                    else -> {}
                }

            }
        }
    )
}

@Composable
fun WeatherDataDisplay(weatherData: WeatherApiResponse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Main Weather Card
        WeatherCard(
            title = "${weatherData.current.temp}°",
            subtitle = weatherData.current.weather.firstOrNull()?.description ?: "No Data", // Fallback text
            backgroundColor = Brush.verticalGradient(
                colors = listOf(Color(0xFF6D9EEB), Color(0xFF3C5A99))
            ),
            iconRes = R.drawable.ic_weather_icon
        )
        Log.e("Ankit","weather type: ${weatherData.current.weather.firstOrNull()?.description}")
        Log.e("Ankit","wind type: ${weatherData.current.wind_speed}")
        Log.e("Ankit","wind type: ${weatherData.current.humidity}")



        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Humidity Card
            WeatherDetailCard(
                title = "Humidity",
                value = "${weatherData.current.humidity}%", // Display humidity
                backgroundColor = Brush.verticalGradient(
                    colors = listOf(Color(0xFF008080), Color(0xFF20B2AA)) // Adjusted colors
                ),
                iconRes = R.drawable.ic_humidity
            )

            // Wind Speed Card
            WeatherDetailCard(
                title = "Wind",
                value = "${weatherData.current.wind_speed} mph", // Display wind speed
                backgroundColor = Brush.verticalGradient(
                    colors = listOf(Color(0xFF4682B4), Color(0xFF5F9EA0)) // Adjusted colors
                ),
                iconRes = R.drawable.ic_wind
            )
        }
    }
}

@Composable
fun WeatherCard(title: String, subtitle: String, backgroundColor: Brush, iconRes: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.Transparent,
        elevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 56.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Text(
                    text = subtitle.capitalize(),
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.White,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun WeatherDetailCard(title: String, value: String, backgroundColor: Brush, iconRes: Int) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(160.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.Transparent,
        elevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                )
                Text(
                    text = value,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White

                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
