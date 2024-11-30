package aung.thiha.photo.album.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import aung.thiha.photo.album.koin.getViewModel
import aung.thiha.photo.album.navigation.Route
import org.jetbrains.compose.resources.painterResource
import photoalbum.composeapp.generated.resources.Res
import photoalbum.composeapp.generated.resources.ic_waffles

@Composable
fun SplashScreen(
    navHostController: NavHostController
) {

    val viewModel = getViewModel<SplashViewModel>()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                SplashEvent.NavigateToPhotoList -> {
                    navHostController.navigate(Route.PhotoList.name) {
                        popUpTo(0)
                    }
                }
                SplashEvent.NavigateToSignin -> {
                    navHostController.navigate(Route.Signin.name) {
                        popUpTo(0)
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Photo Album",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )
    }
}
