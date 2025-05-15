package aung.thiha.design.snackbar

import androidx.compose.material.SnackbarHostState
import org.jetbrains.compose.resources.getString

suspend inline fun SnackbarHostState.showSnackbar(snackbarModel: SnackbarModel) {
    val message = when (val snackbarMessage = snackbarModel.message) {
        is SnackbarMessage.ResourceMessage -> getString(snackbarMessage.value)
        is SnackbarMessage.StringMessage -> snackbarMessage.value
    }
    showSnackbar(message, snackbarModel.actionLabel, snackbarModel.duration)
}
