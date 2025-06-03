package aung.thiha.photo.album.navigation

import aung.thiha.photo.album.authentication.presentation.navigation.AuthenticationNavigator
import aung.thiha.photo.album.authentication.presentation.navigation.SignupRoute
import aung.thiha.photo.album.photos.navigation.PhotoListRoute

class DefaultAuthenticationNavigator(
    private val navigationDispatcher: NavigationDispatcher
) : AuthenticationNavigator {
    override fun navigateUpFromSignup() = navigationDispatcher.navigateUp()

    override fun navigateToSignup() = navigationDispatcher.navigate(SignupRoute)

    override fun navigateToPhotoList() = navigationDispatcher.navigate(PhotoListRoute) {
        popUpTo(0)
    }
}