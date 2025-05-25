package com.tasktrek.presentation.ui.screens.auth.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.tasktrek.BuildConfig
import com.tasktrek.presentation.ui.components.GoogleSignInButton
import com.tasktrek.presentation.ui.screens.auth.viewModel.AuthState
import com.tasktrek.presentation.ui.screens.auth.viewModel.AuthViewModel
import kotlinx.coroutines.launch
import com.tasktrek.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    onRegistrationSuccess: () -> Unit,
    onLoginSuccess: () -> Unit,
    onGoogleSignIn: () -> Unit
) {
    val viewModel: AuthViewModel = koinViewModel()
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.RegistrationSuccess -> {
                onRegistrationSuccess()
                viewModel.resetInputs()
                viewModel.isLoginMode = true
            }
            is AuthState.Success -> {
                onLoginSuccess()
            }
            else -> {}
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!viewModel.isLoginMode) {
            OutlinedTextField(
                value = viewModel.username,
                onValueChange = { viewModel.username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.isLoginMode) {
            GoogleSignInButton(onClick = {
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(BuildConfig.GOOGLE_SERVER_CLIENT_ID)
                    .setAutoSelectEnabled(true)
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val credentialManager = CredentialManager.create(context)

                coroutineScope.launch {
                    try {
                        val result = credentialManager.getCredential(context, request)
                        handleSignIn(result)
                    } catch (e: Exception) {
                        Log.e("AuthScreen", "GetCredentialException", e)
                    }
                }
            })
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (viewModel.isLoginMode) {
                    viewModel.login()
                } else {
                    viewModel.register()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (viewModel.isLoginMode) "Login" else "Register")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { viewModel.isLoginMode = !viewModel.isLoginMode }) {
            Text(
                if (viewModel.isLoginMode) stringResource(R.string.switch_to_register)
                else stringResource(R.string.switch_to_login)
            )
        }

        when (val state = authState) {
            is AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Error -> Text(
                text = state.message,
                color = MaterialTheme.colorScheme.error
            )
            is AuthState.Success -> Text(
                text = "Login successful! Token: ${state.authResult.token}",
                color = MaterialTheme.colorScheme.primary
            )
            AuthState.RegistrationSuccess -> Unit
            AuthState.Idle -> Unit
        }
    }
}

private fun handleSignIn(result: GetCredentialResponse) {
    // Handle the successfully returned credential.
    when (val credential = result.credential) {
        is CustomCredential -> {
            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    // Use googleIdTokenCredential and extract id to validate and
                    // authenticate on your server.
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)

                    val googleIdToken = googleIdTokenCredential.idToken
                    Log.i("answer", googleIdToken)
                    val displayName = googleIdTokenCredential.displayName
                    Log.i("answer", displayName.toString())

                    // TODO: Send [googleIdTokenCredential.idToken] to the backend
                } catch (e: GoogleIdTokenParsingException) {
                    Log.e("MainActivity", "handleSignIn:", e)
                }
            } else {
                Log.e("MainActivity", "Unexpected type of credential")
            }
        }

        else -> {
            Log.e("MainActivity", "Unexpected type of credential")
        }
    }
}
