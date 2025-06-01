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
import aung.thiha.photo.album.koin.getViewModel
import aung.thiha.photo.album.navigation.Route

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
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator()
    }
}
