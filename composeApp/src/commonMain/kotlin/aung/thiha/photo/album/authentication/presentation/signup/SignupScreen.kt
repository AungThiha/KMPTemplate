package aung.thiha.photo.album.authentication.presentation.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import aung.thiha.compose.AlbumTopAppBar
import aung.thiha.compose.LoadingOverlay
import aung.thiha.photo.album.authentication.presentation.signup.signup.SignupScreenListener
import aung.thiha.photo.album.authentication.presentation.signup.signup.SignupViewModel
import aung.thiha.photo.album.koin.getViewModel
import io.github.aungthiha.snackbar.observeWithLifecycle
import io.github.aungthiha.snackbar.showSnackbar

@Composable
internal fun SignupContainer() {
    val viewModel = getViewModel<SignupViewModel>()

    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val confirmPassword by viewModel.confirmPassword.collectAsStateWithLifecycle()
    val overlayLoading by viewModel.overlayLoading.collectAsStateWithLifecycle()

    val snackbarHostState : SnackbarHostState = remember { SnackbarHostState() }
    viewModel.snackbarFlow.observeWithLifecycle {
        snackbarHostState.showSnackbar(it)
    }

    SignupScreen(
        snackbarHostState = snackbarHostState,
        email = email,
        password = password,
        confirmPassword = confirmPassword,
        overlayLoading = overlayLoading,
        eventReceiver = viewModel
    )
}

@Composable
internal fun SignupScreen(
    snackbarHostState: SnackbarHostState,
    email: String,
    password: String,
    confirmPassword: String,
    overlayLoading: Boolean,
    eventReceiver: SignupScreenListener
) {

    val keyboard = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            AlbumTopAppBar {
                eventReceiver.navigateUp()
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
        ) { contentPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to My Photo",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = eventReceiver::updateEmail,
                label = { Text("email") },
                placeholder = { Text("example@example.com") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = eventReceiver::updatePassword,
                label = { Text("password") },
                placeholder = { Text("your password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = eventReceiver::updateConfirmPassword,
                label = { Text("confirm password") },
                placeholder = { Text("confirm your password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    keyboard?.hide()
                    eventReceiver.signup()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Sign up", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
    if (overlayLoading) {
        LoadingOverlay()
    }
}
