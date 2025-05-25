package com.tasktrek

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.tasktrek.presentation.ui.screens.auth.view.AuthScreen
import com.tasktrek.presentation.ui.screens.auth.viewModel.AuthViewModel
import com.tasktrek.presentation.ui.theme.TaskTrekTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskTrekTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = koinViewModel<AuthViewModel>()
                    AuthScreen(
                        viewModel = viewModel,
                        onRegistrationSuccess = {
                            Toast.makeText(
                                this@MainActivity,
                                "Registration successful! Please login",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }
}