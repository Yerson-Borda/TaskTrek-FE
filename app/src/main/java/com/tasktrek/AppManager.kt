package com.tasktrek

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tasktrek.presentation.ui.screens.auth.view.AuthScreen
import com.tasktrek.presentation.ui.screens.home.view.HomeScreen
import kotlinx.serialization.Serializable
import com.tasktrek.presentation.ui.screens.splash.view.SplashScreen

@Composable
fun AppManager() {
    val context = LocalContext.current
    val navController = rememberNavController()
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

                    TODO("Something must go here...")

                },
                onLoginSuccess = {
                    navController.navigate(HomeScreenRoute) {
                        popUpTo(AuthScreenRoute) { inclusive = true }
                    }
                },
                onGoogleSignIn = {

                    TODO("Handle response and google services")

                    navController.navigate(HomeScreenRoute) {
                        popUpTo(AuthScreenRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<HomeScreenRoute> {
            HomeScreen()
        }
    }
}

@Serializable
object SplashScreenRoute

@Serializable
object AuthScreenRoute

@Serializable
object HomeScreenRoute