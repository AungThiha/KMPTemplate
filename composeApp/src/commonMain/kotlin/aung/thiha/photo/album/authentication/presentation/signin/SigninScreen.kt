package aung.thiha.photo.album.authentication.presentation.signin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavHostController
import aung.thiha.photo.album.koin.getViewModel
import aung.thiha.photo.album.design.LoadingOverlay
import aung.thiha.photo.album.navigation.Route

@Composable
fun SigninScreen(
    navHostController: NavHostController
) {

    val viewModel = getViewModel<SigninViewModel>()
    val signinState by remember { viewModel.signinState }

    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    val keyboard = LocalSoftwareKeyboardController.current

    if (messages.isNotEmpty()) {
        val message = messages.first()

        LaunchedEffect(key1 = message) {
            snackbarHostState.showSnackbar(message = message)
            viewModel.setMessageShown(message)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                SigninEvent.NavigateToPhotoList -> {
                    navHostController.navigate(Route.PhotoList.name) {
                        popUpTo(0)
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { contentPadding ->
        
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                value = viewModel.email,
                onValueChange = { viewModel.updateEmail(it) },
                label = { Text("email") },
                placeholder = { Text("example@example.com") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.updatePassword(it) },
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
                    viewModel.signin()
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
                    navHostController.navigate(Route.Signup.name)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Gray
                )
            ) {
                Text(text = "Sign up", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

        }

        if (signinState == SigninState.OverlayLoading) {
            LoadingOverlay()
        }
    }
}

