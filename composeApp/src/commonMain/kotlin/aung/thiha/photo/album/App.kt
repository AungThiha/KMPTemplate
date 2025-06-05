package aung.thiha.photo.album

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import aung.thiha.compose.navigation.Destination
import aung.thiha.coroutines.AppDispatchers
import aung.thiha.photo.album.authentication.presentation.signup.navigation.authentication
import aung.thiha.photo.album.authentication.usecase.AppRestartListener
import aung.thiha.photo.album.navigation.DefaultNavigationDispatcher
import aung.thiha.photo.album.navigation.NavigationHandler
import aung.thiha.photo.album.navigation.NavigationOptions
import aung.thiha.photo.album.navigation.Route
import aung.thiha.photo.album.navigation.toNavOptions
import aung.thiha.photo.album.photos.navigation.photos
import aung.thiha.photo.album.splash.SplashScreen
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController: NavHostController = rememberNavController()
        val lifecycleOwner = LocalLifecycleOwner.current
        val lifecycleScope = lifecycleOwner.lifecycleScope

        AppRestartListener.listener = {
            navController.navigate(Route.Splash.name) {
                popUpTo(0)
            }
        }

        DefaultNavigationDispatcher.setHandler(object : NavigationHandler {
            /**
             * [onNavigateUp] can be called from any thread but
             * [NavHostController.navigateUp] needs to be called from main thread
             * That's why it uses [lifecycleScope] to ensure the function is main-safe
             * This separates the concern from the caller,
             * meaning the caller can use it without worrying about main-safety
            * */
            override fun onNavigateUp(): Deferred<Boolean> = lifecycleScope.async(AppDispatchers.main) {
                navController.navigateUp()
            }

            /**
             * [onNavigation] can be called from any thread but
             * [NavHostController.navigate] needs to be called from main thread
             * That's why it uses [lifecycleScope] to ensure the function is main-safe
             * This separates the concern from the caller,
             * meaning the caller can use it without worrying about main-safety
             * */
            override fun onNavigation(
                destination: Destination,
                navigationOptions: NavigationOptions
            ) = lifecycleScope.launch(AppDispatchers.main) {
                navController.navigate(destination, navigationOptions.toNavOptions())
            }
        })
        NavHost(
            navController = navController,
            startDestination = Route.Splash.name,
            modifier = Modifier
                .fillMaxSize()
        ) {
            composable(route = Route.Splash.name) {
                SplashScreen(navController)
            }
            authentication()
            photos()
        }
    }
}