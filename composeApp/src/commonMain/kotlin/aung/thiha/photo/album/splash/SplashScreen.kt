package aung.thiha.photo.album.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import aung.thiha.photo.album.authentication.presentation.navigation.SigninRoute
import aung.thiha.photo.album.koin.getViewModel
import aung.thiha.photo.album.navigation.Route
import aung.thiha.photo.album.photos.navigation.PhotoListRoute

@Composable
fun SplashScreen(
    navHostController: NavHostController
) {

    val viewModel = getViewModel<SplashViewModel>()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                SplashEvent.NavigateToPhotoList -> {
                    // TODO use navigator
                    navHostController.navigate(PhotoListRoute) {
                        popUpTo(0)
                    }
                }
                SplashEvent.NavigateToSignin -> {
                    // TODO use navigator
                    navHostController.navigate(SigninRoute) {
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
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator()
    }
}
