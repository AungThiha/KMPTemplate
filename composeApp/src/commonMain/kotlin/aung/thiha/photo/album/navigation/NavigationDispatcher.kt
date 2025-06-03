package aung.thiha.photo.album.navigation

import androidx.navigation.NavOptionsBuilder
import aung.thiha.compose.navigation.Destination
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job

interface NavigationHandler {
    fun onNavigateUp() : Deferred<Boolean>
    fun onNavigation(destination: Destination, builder: NavOptionsBuilder.() -> Unit) : Job
}

/**
 * TODO NavOptionsBuilder cannot be asserted. Replace it with a model and map that somewhere else
* */
interface NavigationDispatcher {
    fun setHandler(handler: NavigationHandler)

    fun navigate(
        destination: Destination,
        builder: NavOptionsBuilder.() -> Unit = {}
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
        builder: NavOptionsBuilder.() -> Unit
    ) : Job = handler.onNavigation(destination, builder)

    override fun navigateUp() : Deferred<Boolean> = handler.onNavigateUp()
}
