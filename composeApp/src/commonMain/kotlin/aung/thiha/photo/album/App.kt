package aung.thiha.photo.album

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import aung.thiha.photo.album.login.LoginScreen
import aung.thiha.photo.album.splash.SplashScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import photoalbum.composeapp.generated.resources.Res
import photoalbum.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController: NavHostController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Route.Splash.name,
            modifier = Modifier
                .fillMaxSize()
        ) {
            composable(route = Route.Splash.name) {
                SplashScreen(navController)
            }
            composable(route = Route.Login.name) {
                LoginScreen(navController)
            }
        }
    }
}