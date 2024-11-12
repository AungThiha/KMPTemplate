package aung.thiha.photo.album.splash

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import aung.thiha.photo.album.navigation.Route

@Composable
fun SplashScreen(
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(
            onClick = {
                // TODO edit. Splash Screen should look for authentication token to determine where to go next
                navHostController.navigate(Route.Signin.name) {
                    popUpTo(Route.Signin.name) {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Sign in", color = Color.White)
        }
    }

}