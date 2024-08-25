package com.example.weatherappassignment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherappassignment.ui.screens.LoginScreen
import com.example.weatherappassignment.ui.screens.OnboardingScreen
import com.example.weatherappassignment.ui.screens.UserFormScreen
import com.example.weatherappassignment.ui.screens.UserListScreen
import com.example.weatherappassignment.ui.screens.WeatherScreen

sealed class WeatherScreens(val route : String) {
    object Onboarding : WeatherScreens("onboarding")
    object Login : WeatherScreens("login")
    object UserList : WeatherScreens("user_list")
    object UserForm : WeatherScreens("user_form")
    object Weather : WeatherScreens("weather")

}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = WeatherScreens.Onboarding.route) {
        composable(WeatherScreens.Onboarding.route) { OnboardingScreen(navController) }
        composable(WeatherScreens.Login.route) { LoginScreen(navController) }
        composable(WeatherScreens.UserList.route) { UserListScreen(navController) }
        composable(WeatherScreens.UserForm.route) { UserFormScreen(navController) }
        composable(WeatherScreens.Weather.route) { WeatherScreen(navController) }
    }
}