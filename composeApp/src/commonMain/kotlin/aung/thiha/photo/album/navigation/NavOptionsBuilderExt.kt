package aung.thiha.photo.album.navigation

import androidx.navigation.NavOptionsBuilder
import co.touchlab.kermit.Logger

fun NavOptionsBuilder.applyNavigationOptions(navigationOptions: NavigationOptions) {
    Logger.withTag("navOptions").d("launchSingleTop: $launchSingleTop")
    launchSingleTop = navigationOptions.launchSingleTop

    navigationOptions.backStackOptions?.let {
        when (it) {
            BackStackOptions.Clear -> {
                Logger.withTag("navOptions").d("clearBackStack")
                popUpTo(0)
            }

            is BackStackOptions.PopUpTo -> {
                Logger.withTag("navOptions").d("withPopupOptions")
                popUpTo(it.popUpToDestination) {
                    inclusive = it.inclusive
                }
            }

        }
    }
}