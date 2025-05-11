package aung.thiha.photo.album

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import aung.thiha.photo.album.authentication.usecase.AppRestartListener
import aung.thiha.photo.album.authentication.presentation.signin.SigninScreen
import aung.thiha.photo.album.authentication.presentation.signup.SignupScreen
import aung.thiha.photo.album.navigation.Route
import aung.thiha.photo.album.photos.presentation.PhotoListScreen
import aung.thiha.photo.album.splash.SplashScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
            val navController: NavHostController = rememberNavController()

            AppRestartListener.listener = {
                navController.navigate(Route.Splash.name) {
                    popUpTo(0)
                }
            }
            NavHost(
                navController = navController,
                startDestination = Route.Splash.name,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                composable(route = Route.Splash.name) {
                    SplashScreen(navController)
                }
                composable(route = Route.Signin.name) {
                    SigninScreen(navController)
                }
                composable(route = Route.Signup.name) {
                    SignupScreen(navController)
                }
                composable(route = Route.PhotoList.name) {
                    PhotoListScreen(navController)
                }
            }
        }
}