package aung.thiha.photo.album.authentication.presentation.signup.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import aung.thiha.compose.navigation.Destination
import aung.thiha.photo.album.authentication.presentation.signup.signin.SinginContainer
import aung.thiha.photo.album.authentication.presentation.signup.signup.SignupContainer
import kotlinx.serialization.Serializable

@Serializable
data object SignupRoute : Destination

@Serializable
data object SigninRoute : Destination

fun NavGraphBuilder.authentication() {
    composable<SignupRoute> {
        SignupContainer()
    }
    composable<SigninRoute> {
        SinginContainer()
    }
}
