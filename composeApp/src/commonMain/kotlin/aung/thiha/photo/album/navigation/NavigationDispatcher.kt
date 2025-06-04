package aung.thiha.photo.album.navigation

import aung.thiha.compose.navigation.Destination
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job

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
