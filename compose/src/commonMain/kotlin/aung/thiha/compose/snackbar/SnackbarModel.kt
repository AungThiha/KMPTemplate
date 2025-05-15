package aung.thiha.compose.snackbar

import androidx.compose.material.SnackbarDuration

data class SnackbarModel(
    val message: SnackbarMessage,
    val actionLabel: String?,
    val duration: SnackbarDuration
)
