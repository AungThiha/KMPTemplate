package aung.thiha.photo.album.navigation

import androidx.navigation.NavOptions
import aung.thiha.compose.navigation.Destination

/**
 * The navigation abstraction layer is designed after to Jetpack Navigation so engineers can pick it up quickly.
 * Then, why abstract at all? Mainly to make it easy to swap in another library if needed.
 * When needed to swap in another library, might require a bit of adapter logic
 * but the change will all be isolated inside the abstraction.
 * */
data class PopUpToOptions(
    val popUpToRoute: Destination? = null,
    val inclusive: Boolean = false
)

data class NavigationOptions(
    val launchSingleTop: Boolean = false,
    val clearBackStack: Boolean = true,
    val popUpToOptions: PopUpToOptions? = null
)

class NavigationOptionsBuilder {
    var launchSingleTop: Boolean = false
    var popUpToOptions: PopUpToOptions? = null

    internal fun build(): NavigationOptions = NavigationOptions(
        launchSingleTop = launchSingleTop,
        popUpToOptions = popUpToOptions
    )
}

fun NavigationOptions.toNavOptions() = NavOptions.Builder()
    .setLaunchSingleTop(launchSingleTop)
    .also {
        if (popUpToOptions != null) {
            if (popUpToOptions.popUpToRoute == null)
                it.setPopUpTo(0, false)
            else
                it.setPopUpTo(popUpToOptions.popUpToRoute, inclusive = popUpToOptions.inclusive)
        }
    }
    .build()

fun clearBackStack() = PopUpToOptions()

fun navigationOptions(builder: NavigationOptionsBuilder.() -> Unit): NavigationOptions {
    return NavigationOptionsBuilder().apply(builder).build()
}
