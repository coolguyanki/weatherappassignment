package com.example.weatherappassignment.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.weatherappassignment.UserViewModel
import com.example.weatherappassignment.data.User
import com.example.weatherappassignment.navigation.WeatherScreens
import com.example.weatherappassignment.ui.theme.GradientBrush

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UserFormScreen(navController: NavHostController, viewModel: UserViewModel = viewModel()) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    label = { Text("First Name") }
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    label = { Text("Last Name") }
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    label = { Text("Email") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Button(onClick = {
                        if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty()) {
                            val user =
                                User(firstName = firstName, lastName = lastName, email = email)
                            viewModel.insertUser(user)
                            navController.navigate(WeatherScreens.UserList.route) {
                                popUpTo(WeatherScreens.UserList.route) { inclusive = true }
                            }
                        }
                    }) {
                        Text("Save")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = { navController.popBackStack() }) {
                        Text("Cancel")
                    }
                }
            }
        }
    )
}