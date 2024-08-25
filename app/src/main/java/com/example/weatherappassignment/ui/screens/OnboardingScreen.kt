package com.example.weatherappassignment.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.weatherappassignment.navigation.WeatherScreens
import com.example.weatherappassignment.ui.theme.GradientBrush

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OnboardingScreen(navController: NavHostController) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Welcome to the App!", style = MaterialTheme.typography.h4)
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = {  navController.navigate(WeatherScreens.Login.route) {
                    popUpTo(WeatherScreens.Onboarding.route) { inclusive = true }
                } },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                    Text("Login", style = TextStyle(fontSize = 18.sp))
                }
            }
        }
