package aung.thiha.snackbar

import androidx.compose.material.SnackbarHostState
import org.jetbrains.compose.resources.getString

suspend inline fun SnackbarHostState.showSnackbar(snackbarModel: SnackbarModel) {
    showSnackbar(
        getString(snackbarModel.message),
        snackbarModel.actionLabel?.let { getString(it) },
        snackbarModel.duration
    )
}
