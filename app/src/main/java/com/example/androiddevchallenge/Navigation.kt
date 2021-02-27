/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.Destinations.DETAIL_PUPPY_ID_KEY
import com.example.androiddevchallenge.Destinations.DETAIL_PUPPY_NAME_KEY
import com.example.androiddevchallenge.Destinations.DETAIL_ROUTE
import com.example.androiddevchallenge.Destinations.HOME_ROUTE
import com.example.androiddevchallenge.di.SingletonInjector
import com.example.androiddevchallenge.ui.screens.detail.DetailScreen
import com.example.androiddevchallenge.ui.screens.home.HomeScreen

object Destinations {
    const val HOME_ROUTE = "home"
    const val DETAIL_ROUTE = "detail"
    const val DETAIL_PUPPY_ID_KEY = "puppyId"
    const val DETAIL_PUPPY_NAME_KEY = "puppyName"
}

@Composable
fun Navigation(singletonInjector: SingletonInjector) {
    val navController = rememberNavController()
    val actions = remember(navController) { NavigationActions(navController) }
    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE
    ) {
        composable(HOME_ROUTE) {
            HomeScreen(
                singletonInjector = singletonInjector,
                actions = actions
            )
        }
        composable(
            "$DETAIL_ROUTE/{$DETAIL_PUPPY_ID_KEY}/{$DETAIL_PUPPY_NAME_KEY}",
            arguments = listOf(
                navArgument(DETAIL_PUPPY_ID_KEY) { type = NavType.LongType },
                navArgument(DETAIL_PUPPY_NAME_KEY) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            DetailScreen(
                singletonInjector = singletonInjector,
                actions = actions,
                puppyId = arguments.getLong(DETAIL_PUPPY_ID_KEY),
                puppyName = arguments.getString(DETAIL_PUPPY_NAME_KEY)!!
            )
        }
    }
}

class NavigationActions(navController: NavController) {
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
    val navigateToPuppyDetails: (Long, String) -> Unit = { id, name ->
        navController.navigate("$DETAIL_ROUTE/$id/$name")
    }
}
