package aung.thiha.design.snackbar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.StringResource

interface SnackbarChannelOwner {
    fun CoroutineScope.showSnackBar(message: String) : Job
    fun CoroutineScope.showSnackBar(message: StringResource) : Job
    val snackbarFlow: Flow<SnackbarChannel.SnackbarMessage>
}