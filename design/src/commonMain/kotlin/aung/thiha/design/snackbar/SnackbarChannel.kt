package aung.thiha.design.snackbar

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.flow.receiveAsFlow
import org.jetbrains.compose.resources.StringResource
import kotlin.jvm.JvmInline

class SnackbarChannel : SnackbarChannelOwner {

    private val snackbarMessages: Channel<SnackbarMessage> = Channel(Channel.BUFFERED)
    override val snackbarFlow = snackbarMessages.receiveAsFlow()

    /**
     * If the buffer is full, showing Snackbar will fail
     * If it fails, it's recommended to make sure your UI logic doesn't emit too many snackbars
     * After all, buffer size is 64. It's impossible to have 64 snackbars in a short period time
     * unless there's a serious error
     * */
    override fun showSnackBar(message: String): ChannelResult<Unit> =
        snackbarMessages.trySend(SnackbarMessage.StringMessage(message))

    /**
     * If the buffer is full, showing Snackbar will fail
     * If it fails, it's recommended to make sure your UI logic doesn't emit too many snackbars
     * After all, buffer size is 64. It's impossible to have 64 snackbars in a short period time
     * unless there's a serious error
     * */
    override fun showSnackBar(message: StringResource): ChannelResult<Unit> =
        snackbarMessages.trySend(SnackbarMessage.ResourceMessage(message))

    sealed interface SnackbarMessage {
        @JvmInline
        value class StringMessage(val value: String) : SnackbarMessage

        @JvmInline
        value class ResourceMessage(val value: StringResource) : SnackbarMessage
    }
}
