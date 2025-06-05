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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.tasktrek.presentation.ui.screens.common.FloatingBottomBar
import com.tasktrek.presentation.ui.screens.files.view.FilesScreen
import com.tasktrek.presentation.ui.screens.task_creation.view.TaskCreationScreen

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
            var showFloatingMenu by remember { mutableStateOf(false) }

            Scaffold(
                bottomBar = {
                    FloatingBottomBar(
                        onHomeClick = { navController.navigate(HomeScreenRoute) },
                        onAddClick = { showFloatingMenu = !showFloatingMenu },
                        onFilesClick = { navController.navigate(FilesScreenRoute) },
                        currentDestination = currentDestination ?: ""
                    )
                }
            ) { paddingValues ->
                HomeScreen(
                    modifier = Modifier.padding(paddingValues),
                    showFloatingMenu = showFloatingMenu,
                    onDismissFloatingMenu = { showFloatingMenu = false },
                    onCreateTask = { navController.navigate(TaskCreationScreenRoute) }
                )
            }
        }
        composable<FilesScreenRoute> {
            var showFloatingMenu by remember { mutableStateOf(false) }
            Scaffold(
                bottomBar = {
                    FloatingBottomBar(
                        onHomeClick = { navController.navigate(HomeScreenRoute) },
                        onAddClick = { showFloatingMenu = !showFloatingMenu },
                        onFilesClick = { navController.navigate(FilesScreenRoute) },
                        currentDestination = currentDestination ?: ""
                    )
                }
            ) { paddingValues ->
                FilesScreen(
                    modifier = Modifier.padding(paddingValues),
                    showFloatingMenu = showFloatingMenu,
                    onDismissFloatingMenu = { showFloatingMenu = false }
                )
            }
        }
        composable<TaskCreationScreenRoute> {
            TaskCreationScreen()
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

@Serializable
object TaskCreationScreenRoute