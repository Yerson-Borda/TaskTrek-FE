package com.tasktrek

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tasktrek.presentation.ui.screens.auth.view.AuthScreen
import com.tasktrek.presentation.ui.screens.home.view.HomeScreen
import kotlinx.serialization.Serializable
import com.tasktrek.presentation.ui.screens.splash.view.SplashScreen
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.tasktrek.presentation.ui.screens.common.FloatingBottomBar
import com.tasktrek.presentation.ui.screens.files.view.FilesScreen

@Composable
fun AppManager() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    NavHost(
        navController = navController,
        startDestination = SplashScreenRoute
    ) {
        composable<SplashScreenRoute> {
            SplashScreen {
                navController.navigate(AuthScreenRoute) {
                    popUpTo(SplashScreenRoute) { inclusive = true }
                }
            }
        }
        composable<AuthScreenRoute> {
            AuthScreen(
                onRegistrationSuccess = {
                    navController.navigate(HomeScreenRoute) {
                        popUpTo(AuthScreenRoute) { inclusive = true }
                    }
                },
                onLoginSuccess = {
                    navController.navigate(HomeScreenRoute) {
                        popUpTo(AuthScreenRoute) { inclusive = true }
                    }
                },
                onGoogleSignIn = {
                    navController.navigate(HomeScreenRoute) {
                        popUpTo(AuthScreenRoute) { inclusive = true }
                    }
                }
            )
        }
        composable<HomeScreenRoute> {
            Scaffold(
                bottomBar = {
                    FloatingBottomBar(
                        onHomeClick = { navController.navigate(HomeScreenRoute) },
                        onAddClick = { /* TODO: Open add options dialog */ },
                        onFilesClick = { navController.navigate(FilesScreenRoute) },
                        currentDestination = currentDestination ?: ""
                    )
                }
            ) { paddingValues ->
                HomeScreen(modifier = Modifier.padding(paddingValues))
            }
        }
        composable<FilesScreenRoute> {
            Scaffold(
                bottomBar = {
                    FloatingBottomBar(
                        onHomeClick = { navController.navigate(HomeScreenRoute) },
                        onAddClick = { /* TODO: Open add options dialog */ },
                        onFilesClick = { navController.navigate(FilesScreenRoute) },
                        currentDestination = currentDestination ?: ""
                    )
                }
            ) { paddingValues ->
                FilesScreen(modifier = Modifier.padding(paddingValues))
            }
        }
    }
}


@Serializable
object SplashScreenRoute

@Serializable
object AuthScreenRoute

@Serializable
object HomeScreenRoute

@Serializable
object FilesScreenRoute