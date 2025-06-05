package aung.thiha.photo.album.navigation

import androidx.navigation.NavOptions
import aung.thiha.compose.navigation.Destination

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
