package aung.thiha.compose.snackbar

import androidx.compose.material.SnackbarDuration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.flow.receiveAsFlow
import org.jetbrains.compose.resources.StringResource

class SnackbarChannel : SnackbarChannelOwner {

    private val snackbarMessages: Channel<SnackbarModel> = Channel(Channel.BUFFERED)
    override val snackbarFlow = snackbarMessages.receiveAsFlow()

    /**
     * If the buffer is full, showing Snackbar will fail
     * If it fails, it's recommended to make sure your UI logic doesn't emit too many snackbars
     * After all, buffer size is 64. It's impossible to have 64 snackbars in a short period time
     * unless there's a serious error
     * */
    override fun showSnackBar(message: String, actionLabel: String?, duration: SnackbarDuration): ChannelResult<Unit> =
        snackbarMessages.trySend(
            SnackbarModel(
                message = SnackbarMessage.StringMessage(message),
                actionLabel = actionLabel,
                duration = duration
            )
        )

    /**
     * If the buffer is full, showing Snackbar will fail
     * If it fails, it's recommended to make sure your UI logic doesn't emit too many snackbars
     * After all, buffer size is 64. It's impossible to have 64 snackbars in a short period time
     * unless there's a serious error
     * */
    override fun showSnackBar(message: StringResource, actionLabel: String?, duration: SnackbarDuration): ChannelResult<Unit> =
        snackbarMessages.trySend(
            SnackbarModel(
                message = SnackbarMessage.ResourceMessage(message),
                actionLabel = actionLabel,
                duration = duration
            )
        )

}
