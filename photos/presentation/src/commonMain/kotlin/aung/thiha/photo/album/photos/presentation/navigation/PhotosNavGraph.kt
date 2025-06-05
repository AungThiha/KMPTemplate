package aung.thiha.photo.album.photos.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import aung.thiha.compose.navigation.Destination
import aung.thiha.photo.album.photos.presentation.overview.PhotoListContainer
import kotlinx.serialization.Serializable

@Serializable
data object PhotoListRoute : Destination

fun NavGraphBuilder.photos() {
    composable<PhotoListRoute> {
        PhotoListContainer()
    }
}