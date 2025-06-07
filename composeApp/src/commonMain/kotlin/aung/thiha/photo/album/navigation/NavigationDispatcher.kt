package aung.thiha.photo.album.navigation

import aung.thiha.compose.navigation.Destination
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job

/**
 * The navigation abstraction layer is designed after Jetpack Navigation so engineers can pick it up quickly.
 * Then, why abstract at all? Mainly to make it easy to swap in another library if needed.
 * When needed to swap in another library, might require a bit of adapter logic
 * but the change will all be isolated inside the abstraction.
 * */
interface NavigationHandler {
    fun onNavigateUp(): Deferred<Boolean>
    fun onNavigation(destination: Destination, navigationOptions: NavigationOptions): Job
}

interface NavigationDispatcher {
    fun setHandler(handler: NavigationHandler)

    fun navigate(
        destination: Destination,
        launchSingleTop: Boolean = false,
        clearBackStack: Boolean = false
    ): Job

    fun navigate(
        destination: Destination,
        popUpTo: Destination,
        isInclusive: Boolean = false
    ): Job

    fun navigate(
        destination: Destination,
        launchSingleTop: Boolean,
        popUpTo: Destination,
        isInclusive: Boolean = false
    ): Job


    fun navigateUp(): Deferred<Boolean>
}

object DefaultNavigationDispatcher : NavigationDispatcher {

    private lateinit var handler: NavigationHandler

    override fun setHandler(handler: NavigationHandler) {
        this.handler = handler
    }

    override fun navigate(
        destination: Destination,
        launchSingleTop: Boolean,
        clearBackStack: Boolean,
    ): Job = handler.onNavigation(
        destination = destination,
        NavigationOptions(
            launchSingleTop = launchSingleTop,
            backStackOptions = if (clearBackStack) BackStackOptions.Clear else null
        )
    )

    override fun navigate(
        destination: Destination,
        popUpTo: Destination,
        isInclusive: Boolean
    ): Job = handler.onNavigation(
        destination = destination,
        NavigationOptions(
            launchSingleTop = false,
            backStackOptions = BackStackOptions.PopUpTo(popUpTo, isInclusive)
        )
    )

    override fun navigate(
        destination: Destination,
        launchSingleTop: Boolean,
        popUpTo: Destination,
        isInclusive: Boolean
    ): Job = handler.onNavigation(
        destination = destination,
        NavigationOptions(
            launchSingleTop = launchSingleTop,
            backStackOptions = BackStackOptions.PopUpTo(popUpTo, isInclusive)
        )
    )

    override fun navigateUp(): Deferred<Boolean> = handler.onNavigateUp()
}
