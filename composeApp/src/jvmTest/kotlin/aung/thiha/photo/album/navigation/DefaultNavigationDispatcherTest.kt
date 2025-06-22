package aung.thiha.photo.album.navigation

import aung.thiha.photo.album.authentication.presentation.signup.navigation.SigninRoute
import aung.thiha.photo.album.photos.presentation.navigation.PhotoListRoute
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.Job
import org.junit.jupiter.api.Test

class DefaultNavigationDispatcherTest {

    private val navigationDispatcher = DefaultNavigationDispatcher()
    private val handler = mock<NavigationHandler>()

    @Test
    fun `navigate should call handler with launchSingleTop = true`() {
        navigationDispatcher.setHandler(handler)
        every { handler.onNavigation(any(), any()) } returns Job()

        navigationDispatcher.navigate(PhotoListRoute, launchSingleTop = true)

        verify {
            handler.onNavigation(
                PhotoListRoute,
                NavigationOptions(
                    launchSingleTop = true
                )
            )
        }
    }

    @Test
    fun `navigate should call handler with popUpToRoute = SigninRoute`() {
        navigationDispatcher.setHandler(handler)
        every { handler.onNavigation(any(), any()) } returns Job()

        navigationDispatcher.navigate(PhotoListRoute, popUpTo = SigninRoute)

        verify {
            handler.onNavigation(
                PhotoListRoute,
                NavigationOptions(
                    backStackOptions = BackStackOptions.PopUpTo(SigninRoute)
                )
            )
        }
    }

    @Test
    fun `navigate should call handler with inclusive = true`() {
        navigationDispatcher.setHandler(handler)
        every { handler.onNavigation(any(), any()) } returns Job()

        navigationDispatcher.navigate(PhotoListRoute, popUpTo = SigninRoute, isInclusive = true)

        verify {
            handler.onNavigation(
                PhotoListRoute,
                NavigationOptions(
                    backStackOptions = BackStackOptions.PopUpTo(SigninRoute, true)
                )
            )
        }
    }

    @Test
    fun `navigate should call handler with null popup destination`() {
        navigationDispatcher.setHandler(handler)
        every { handler.onNavigation(any(), any()) } returns Job()

        navigationDispatcher.navigate(PhotoListRoute, clearBackStack = true)

        verify {
            handler.onNavigation(
                PhotoListRoute,
                NavigationOptions(
                    backStackOptions = BackStackOptions.Clear
                )
            )
        }
    }
}
