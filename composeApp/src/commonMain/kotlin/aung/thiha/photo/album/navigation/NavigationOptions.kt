package aung.thiha.photo.album.navigation

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

fun clearBackStack() = PopUpToOptions()

fun navigationOptions(builder: NavigationOptionsBuilder.() -> Unit): NavigationOptions {
    return NavigationOptionsBuilder().apply(builder).build()
}
