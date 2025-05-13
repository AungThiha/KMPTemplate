package aung.thiha.design.snackbar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import kotlin.jvm.JvmInline

class SnackbarChannel : SnackbarChannelOwner {

    private val snackbarMessages: Channel<SnackbarMessage> = Channel(Channel.BUFFERED)
    override val snackbarFlow = snackbarMessages.receiveAsFlow()

    override fun CoroutineScope.showSnackBar(message: String) : Job = launch {
        snackbarMessages.send(SnackbarMessage.StringMessage(message))
    }

    override fun CoroutineScope.showSnackBar(message: StringResource): Job = launch {
        snackbarMessages.send(SnackbarMessage.ResourceMessage(message))
    }

    override suspend fun showSnackBar(message: String) {
        snackbarMessages.send(SnackbarMessage.StringMessage(message))
    }

    override suspend fun showSnackBar(message: StringResource) {
        snackbarMessages.send(SnackbarMessage.ResourceMessage(message))
    }

    sealed interface SnackbarMessage {
        @JvmInline
        value class StringMessage(val value: String) : SnackbarMessage

        @JvmInline
        value class ResourceMessage(val value: StringResource) : SnackbarMessage
    }
}
