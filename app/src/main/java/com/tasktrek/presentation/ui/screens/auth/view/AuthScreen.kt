package com.tasktrek.presentation.ui.screens.auth.view

import android.credentials.GetCredentialException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.tasktrek.presentation.ui.components.GoogleSignInButton
import com.tasktrek.presentation.ui.screens.auth.viewModel.AuthState
import com.tasktrek.presentation.ui.screens.auth.viewModel.AuthViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    onRegistrationSuccess: () -> Unit = {},
    onGoogleSignIn: () -> Unit = {}
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoginMode by remember { mutableStateOf(true) }

    val authState by viewModel.authState.collectAsStateWithLifecycle()

    // LocalContext for getting the context needed for CredentialManager
    val context = LocalContext.current

    LaunchedEffect(authState) {
        if (authState is AuthState.RegistrationSuccess) {
            onRegistrationSuccess()
            isLoginMode = true
            username = ""
            email = ""
            password = ""
        }
    }

    // Remember coroutine scope for launching the sign-in task
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!isLoginMode) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoginMode) {
            GoogleSignInButton(onClick = {
                val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId("652218564625-vuet4l64jbtjnrq4ckb3b7pceh5vkre3.apps.googleusercontent.com")
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
                    } catch (e: GetCredentialException) {
                        Log.e("MainActivity", "GetCredentialException", e)
                    }
                }
            }) // Trigger the sign-in
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isLoginMode) {
                    viewModel.login(email, password)
                } else {
                    viewModel.register(username, email, password)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLoginMode) "Login" else "Register")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { isLoginMode = !isLoginMode }
        ) {
            Text(if (isLoginMode) "Switch to Register" else "Switch to Login")
        }

        when (val state = authState) {
            is AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Error -> Text(
                text = state.message,
                color = MaterialTheme.colorScheme.error
            )
            is AuthState.Success -> Text(
                text = "Login successful! Token: ${state.authResponse.token}",
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
