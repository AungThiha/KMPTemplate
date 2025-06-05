package aung.thiha.photo.album.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import aung.thiha.compose.navigation.Destination

import kotlinx.serialization.Serializable

@Serializable
data object SplashRoute : Destination

fun NavGraphBuilder.splash() {
    composable<SplashRoute> {
        SplashContainer()
    }
}
