package aung.thiha.photo.album.authentication.presentation.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import aung.thiha.compose.LoadingOverlay
import aung.thiha.photo.album.authentication.presentation.signup.signin.SigninScreenListener
import aung.thiha.photo.album.authentication.presentation.signup.signin.SigninViewModel
import io.github.aungthiha.snackbar.observeWithLifecycle
import io.github.aungthiha.snackbar.showSnackbar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SinginContainer() {
    val viewModel = koinViewModel<SigninViewModel>()

    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val overlayLoading by viewModel.overlayLoading.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    viewModel.snackbarFlow.observeWithLifecycle {
        snackbarHostState.showSnackbar(it)
    }

    SigninScreen(
        snackbarHostState = snackbarHostState,
        email = email,
        password = password,
        overlayLoading = overlayLoading,
        listener = viewModel
    )
}

@Composable
fun SigninScreen(
    snackbarHostState: SnackbarHostState,
    email: String,
    password: String,
    overlayLoading: Boolean,
    listener: SigninScreenListener
) {

    val keyboard = LocalSoftwareKeyboardController.current


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
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
                onValueChange = listener::updateEmail,
                label = { Text("email") },
                placeholder = { Text("example@example.com") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = listener::updatePassword,
                label = { Text("password") },
                placeholder = { Text("your password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    keyboard?.hide()
                    listener.signin()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Sign in", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    listener.navigateToSignup()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                )
            ) {
                Text(text = "Sign up", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

        }

        if (overlayLoading) {
            LoadingOverlay()
        }
    }
}
