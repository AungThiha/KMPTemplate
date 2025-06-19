package aung.thiha.photo.album

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import aung.thiha.compose.navigation.Destination
import aung.thiha.coroutines.AppDispatchers
import aung.thiha.photo.album.authentication.presentation.signup.navigation.authentication
import aung.thiha.photo.album.navigation.DefaultNavigationDispatcher
import aung.thiha.photo.album.navigation.NavigationDispatcher
import aung.thiha.photo.album.navigation.NavigationHandler
import aung.thiha.photo.album.navigation.NavigationOptions
import aung.thiha.photo.album.navigation.applyNavigationOptions
import aung.thiha.photo.album.photos.presentation.navigation.photos
import aung.thiha.photo.album.splash.SplashRoute
import aung.thiha.photo.album.splash.splash
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController: NavHostController = rememberNavController()
        val lifecycleOwner = LocalLifecycleOwner.current
        val lifecycleScope = lifecycleOwner.lifecycleScope

        koinInject<NavigationDispatcher>().setHandler(object : NavigationHandler {
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
                navController.navigate(destination) {
                    applyNavigationOptions(navigationOptions)
                }
            }
        })
        NavHost(
            navController = navController,
            startDestination = SplashRoute,
            modifier = Modifier
                .fillMaxSize()
        ) {
            splash()
            authentication()
            photos()
        }
    }
}