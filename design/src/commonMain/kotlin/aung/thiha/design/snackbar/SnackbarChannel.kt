package aung.thiha.design.snackbar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import kotlin.jvm.JvmInline

class SnackbarChannel(
    private val coroutineScope: CoroutineScope
) {

    private val snackbarMessages: Channel<SnackbarMessage> = Channel(Channel.BUFFERED)

    fun showSnackBar(message: String): Job = coroutineScope.launch {
        snackbarMessages.send(SnackbarMessage.StringMessage(message))
    }

    fun showSnackBar(message: StringResource): Job = coroutineScope.launch {
        snackbarMessages.send(SnackbarMessage.ResourceMessage(message))
    }

    fun receiveAsFlow(): Flow<SnackbarMessage> = snackbarMessages.receiveAsFlow()

    sealed interface SnackbarMessage {
        @JvmInline
        value class StringMessage(val value: String) : SnackbarMessage

        @JvmInline
        value class ResourceMessage(val value: StringResource) : SnackbarMessage
    }
}
