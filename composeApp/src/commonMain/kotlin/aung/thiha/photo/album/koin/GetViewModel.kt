package aung.thiha.photo.album.koin

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.compose.getKoin

@Composable
inline fun <reified T : ViewModel> getViewModel() : T {
    /**
     * Latest koin version provides a better API for scoping ViewModel to LocalViewModelStoreOwner
     * If you do not know what LocalViewModelStoreOwner is, don't worry about it for now
     * You can come back to this once you have learned the basics
     * */
    val koin = getKoin()
    return viewModel {
        koin.get<T>()
    }
}
