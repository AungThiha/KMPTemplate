package aung.thiha.photo.album.navigation

import aung.thiha.compose.navigation.Destination
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job

/**
 * The navigation abstraction layer is designed after to Jetpack Navigation so engineers can pick it up quickly.
 * Then, why abstract at all? Mainly to make it easy to swap in another library if needed.
 * When needed to swap in another library, might require a bit of adapter logic
 * but the change will all be isolated inside the abstraction.
* */
interface NavigationHandler {
    fun onNavigateUp() : Deferred<Boolean>
    fun onNavigation(destination: Destination, navigationOptions: NavigationOptions) : Job
}

interface NavigationDispatcher {
    fun setHandler(handler: NavigationHandler)

    fun navigate(
        destination: Destination,
        builder: NavigationOptionsBuilder.() -> Unit = {}
    ) : Job

    fun navigateUp() : Deferred<Boolean>
}

object DefaultNavigationDispatcher : NavigationDispatcher {

    private lateinit var handler : NavigationHandler

    override fun setHandler(handler: NavigationHandler) {
        this.handler = handler
    }

    override fun navigate(
        destination: Destination,
        builder: NavigationOptionsBuilder.() -> Unit
    ) : Job = handler.onNavigation(destination, navigationOptions(builder))

    override fun navigateUp() : Deferred<Boolean> = handler.onNavigateUp()
}
