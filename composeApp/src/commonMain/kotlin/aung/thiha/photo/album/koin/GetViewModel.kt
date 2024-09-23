package aung.thiha.photo.album.koin

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.compose.getKoin

@Composable
inline fun <reified T : ViewModel> getViewModel() : T {
    val koin = getKoin()
    return viewModel {
        koin.get<T>()
    }
}
