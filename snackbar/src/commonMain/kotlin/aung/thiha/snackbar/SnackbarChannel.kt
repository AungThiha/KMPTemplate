package aung.thiha.snackbar

import androidx.compose.material.SnackbarDuration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.flow.receiveAsFlow
import org.jetbrains.compose.resources.StringResource

class SnackbarChannel : SnackbarChannelOwner {

    private val snackbarMessages: Channel<SnackbarModel> = Channel(Channel.UNLIMITED)
    override val snackbarFlow = snackbarMessages.receiveAsFlow()

    override fun showSnackBar(message: String, actionLabel: String?, duration: SnackbarDuration): ChannelResult<Unit> =
        snackbarMessages.trySend(
            SnackbarModel(
                message = SnackbarMessage.StringMessage(message),
                actionLabel = actionLabel,
                duration = duration
            )
        )

    override fun showSnackBar(message: StringResource, actionLabel: String?, duration: SnackbarDuration): ChannelResult<Unit> =
        snackbarMessages.trySend(
            SnackbarModel(
                message = SnackbarMessage.ResourceMessage(message),
                actionLabel = actionLabel,
                duration = duration
            )
        )

}
