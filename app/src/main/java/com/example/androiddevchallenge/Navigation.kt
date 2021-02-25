package com.example.androiddevchallenge

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.di.SingletonInjector
import com.example.androiddevchallenge.ui.screens.DetailScreen
import com.example.androiddevchallenge.ui.screens.HomeScreen

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Detail: Screen("detail")
}

@Composable
fun Navigation(singletonInjector: SingletonInjector) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                singletonInjector = singletonInjector,
                navController = navController
            )
        }
        composable(Screen.Detail.route) {
            DetailScreen(
                singletonInjector = singletonInjector,
                navController = navController
            )
        }
    }
}