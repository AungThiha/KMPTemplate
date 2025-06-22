package aung.thiha.photo.album.navigation

import aung.thiha.compose.navigation.Destination

data class DestinationWithOptions(
    val destination: Destination,
    val launchSingleTop: Boolean = false,
    val backStackOptions: BackStackOptions? = null,
)

val Destination.withLaunchSingleTop: DestinationWithOptions
    get() = DestinationWithOptions(
        destination = this,
        launchSingleTop = true
    )


val Destination.withClearBackStack: DestinationWithOptions
    get() = DestinationWithOptions(
        destination = this,
        backStackOptions = BackStackOptions.Clear
    )

fun Destination.withPopUpTo(popUpTo: Destination, isInclusive: Boolean = false): DestinationWithOptions =
    DestinationWithOptions(
        destination = this,
        backStackOptions = BackStackOptions.PopUpTo(popUpTo, isInclusive)
    )
