package aung.thiha.snackbar

import androidx.compose.material.SnackbarHostState
import org.jetbrains.compose.resources.getString

suspend inline fun SnackbarHostState.showSnackbar(snackbarModel: SnackbarModel) {
    // TODO instead of mapping it here, see if StringResource can be converted into String where context isn't available
    // think about testing too. May be we don't need SnackbarMessage at all
    // and we can just possibly do SnackbarModel(getString(stringResource)) right in SnackbarChannel class
    val message = when (val snackbarMessage = snackbarModel.message) {
        is SnackbarMessage.ResourceMessage -> getString(snackbarMessage.value)
        is SnackbarMessage.StringMessage -> snackbarMessage.value
    }
    showSnackbar(message, snackbarModel.actionLabel, snackbarModel.duration)
}
