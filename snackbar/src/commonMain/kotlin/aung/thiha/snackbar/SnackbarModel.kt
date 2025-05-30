package aung.thiha.snackbar

import androidx.compose.material.SnackbarDuration
import org.jetbrains.compose.resources.StringResource

data class SnackbarModel(
    val message: StringResource,
    val actionLabel: StringResource?,
    val duration: SnackbarDuration
)
