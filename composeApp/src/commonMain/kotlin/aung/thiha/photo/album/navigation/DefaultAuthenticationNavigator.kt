package aung.thiha.photo.album.navigation

import aung.thiha.photo.album.authentication.presentation.signup.navigation.SignupRoute
import aung.thiha.photo.album.authentication.presentation.signup.navigation.AuthenticationNavigator
import aung.thiha.photo.album.photos.presentation.navigation.PhotoListRoute

class DefaultAuthenticationNavigator(
    private val navigationDispatcher: NavigationDispatcher
) : AuthenticationNavigator {
    override fun navigateUpFromSignup() = navigationDispatcher.navigateUp()

    override fun navigateToSignup() = navigationDispatcher.navigate(SignupRoute)

    override fun navigateToPhotoList() = navigationDispatcher.navigate(PhotoListRoute) {
        popUpToOptions = clearBackStack()
    }
}