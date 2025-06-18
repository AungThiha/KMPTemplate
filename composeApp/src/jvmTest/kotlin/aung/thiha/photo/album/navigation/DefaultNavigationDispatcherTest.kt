package aung.thiha.photo.album.navigation

import aung.thiha.photo.album.authentication.presentation.signup.navigation.SigninRoute
import aung.thiha.photo.album.photos.presentation.navigation.PhotoListRoute
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.Job
import kotlin.test.Test

class DefaultNavigationDispatcherTest {

    private val handler = mock<NavigationHandler>()

    @Test
    fun `navigate should call handler with launchSingleTop = true`() {
        DefaultNavigationDispatcher.setHandler(handler)
        every { handler.onNavigation(any(), any()) } returns Job()

        DefaultNavigationDispatcher.navigate(PhotoListRoute, launchSingleTop = true)

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
        DefaultNavigationDispatcher.setHandler(handler)
        every { handler.onNavigation(any(), any()) } returns Job()

        DefaultNavigationDispatcher.navigate(PhotoListRoute, popUpTo = SigninRoute)

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
        DefaultNavigationDispatcher.setHandler(handler)
        every { handler.onNavigation(any(), any()) } returns Job()

        DefaultNavigationDispatcher.navigate(PhotoListRoute, popUpTo = SigninRoute, isInclusive = true)

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
        DefaultNavigationDispatcher.setHandler(handler)
        every { handler.onNavigation(any(), any()) } returns Job()

        DefaultNavigationDispatcher.navigate(PhotoListRoute, clearBackStack = true)

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
