package com.tasktrek.presentation.ui.screens.auth.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.withStyle
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
import com.tasktrek.presentation.ui.screens.auth.components.GoogleSignInButton
import com.tasktrek.presentation.ui.screens.auth.viewModel.AuthViewModel
import kotlinx.coroutines.launch
import com.tasktrek.R
import com.tasktrek.presentation.ui.screens.common.TopOvalBackground
import org.koin.androidx.compose.koinViewModel
import com.tasktrek.presentation.ui.screens.auth.components.Email
import com.tasktrek.presentation.ui.screens.auth.components.Password
import com.tasktrek.presentation.ui.screens.auth.components.Username

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
    val localFocusManager = LocalFocusManager.current

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.RegistrationSuccess -> {
                onRegistrationSuccess()
                viewModel.resetInputs()
                viewModel.toggleLoginMode()
            }

            is AuthState.Success -> onLoginSuccess()
            else -> Unit
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopOvalBackground()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier.size(76.dp)
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append("TASK")
                    }
                    withStyle(style = SpanStyle(color = Color(0xFF5F33E1))) {
                        append("TREK")
                    }
                },
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = if (viewModel.isLoginMode) stringResource(R.string.login) else
                    stringResource(R.string.sign_up),
                style = MaterialTheme.typography.titleLarge
            )
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 300.dp, start = 38.dp, end = 38.dp),
//                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!viewModel.isLoginMode) {
                Username(
                    username = viewModel.usernameState.text,
                    error = viewModel.usernameState.error,
                    onUsernameChanged = {
                        viewModel.usernameState.text = it
                        viewModel.usernameState.validate()
                    },
                    onImeAction = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    }
                )

                Spacer(modifier = Modifier.height(25.dp))
            }

            Email(
                email = viewModel.emailState.text,
                error = viewModel.emailState.error,
                onEmailChanged = {
                    viewModel.emailState.text = it
                    viewModel.emailState.validate()
                },
                onImeAction = {
                    localFocusManager.moveFocus(FocusDirection.Down)
                }
            )

            Spacer(modifier = Modifier.height(25.dp))

            Password(
                password = viewModel.passwordState.text,
                error = viewModel.passwordState.error,
                onPasswordChanged = {
                    viewModel.passwordState.text = it
                    viewModel.passwordState.validate()
                },
                onImeAction = {
                    localFocusManager.clearFocus()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (viewModel.isLoginMode) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.forgot_password),
                        color = Color(0xFF5F33E1),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable(
                            onClick = { /* TODO: Forgot Password Action */ }
                        )
                    )

                    Button(
                        onClick = { viewModel.login() },
                        modifier = Modifier
                            .defaultMinSize(minWidth = 120.dp)
                            .height(45.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF5F33E1),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text("Login".uppercase(), fontWeight = Bold)
                    }
                }

                Spacer(modifier = Modifier.height(17.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Divider(modifier = Modifier.weight(1f))
                    Text(
                        text = "  or  ",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray
                    )
                    Divider(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(26.dp))

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

                Spacer(modifier = Modifier.height(11.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { /* Facebook auth */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(45.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1877F2)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.vk_icon),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Text(
                                stringResource(R.string.vk),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Button(
                        onClick = { /* Apple auth */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(45.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.apple_icon),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Text(
                                stringResource(R.string.apple),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            if (!viewModel.isLoginMode) {
                Button(
                    onClick = {
                        viewModel.register()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5F33E1),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text("Register")
                }
                Spacer(modifier = Modifier.height(62.dp))
            }

            LoginToggleText(
                isLoginMode = viewModel.isLoginMode,
                onToggle = { viewModel.toggleLoginMode() }
            )

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

                AuthState.RegistrationSuccess, AuthState.Idle -> Unit
            }
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

@Composable
fun ErrorField(error: String){
    Text(
        text = error,
        color = Color(0xFF9a2d30),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 6.dp),
        style = MaterialTheme.typography.labelSmall,
    )
}