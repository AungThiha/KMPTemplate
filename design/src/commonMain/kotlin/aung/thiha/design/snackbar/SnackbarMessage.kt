package aung.thiha.design.snackbar

import org.jetbrains.compose.resources.StringResource
import kotlin.jvm.JvmInline

sealed interface SnackbarMessage {
    @JvmInline
    value class StringMessage(val value: String) : SnackbarMessage

    @JvmInline
    value class ResourceMessage(val value: StringResource) : SnackbarMessage
}