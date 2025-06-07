package aung.thiha.photo.album.navigation

import aung.thiha.compose.navigation.Destination

/**
 * The navigation abstraction layer is designed after to Jetpack Navigation so engineers can pick it up quickly.
 * Then, why abstract at all? Mainly to make it easy to swap in another library if needed.
 * When needed to swap in another library, might require a bit of adapter logic
 * but the change will all be isolated inside the abstraction.
 * */
sealed class BackStackOptions {
    data object Clear : BackStackOptions()
    data class PopUpTo(
        val popUpToDestination: Destination,
        val inclusive: Boolean = false
    ) : BackStackOptions()
}

data class NavigationOptions(
    val launchSingleTop: Boolean = false,
    val backStackOptions: BackStackOptions? = null
)
