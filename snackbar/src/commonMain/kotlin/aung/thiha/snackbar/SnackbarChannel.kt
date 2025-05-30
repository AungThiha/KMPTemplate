package aung.thiha.snackbar

import androidx.compose.material.SnackbarDuration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.flow.receiveAsFlow
import org.jetbrains.compose.resources.StringResource

class SnackbarChannel : SnackbarChannelOwner {

    private val snackbarMessages: Channel<SnackbarModel> = Channel(Channel.UNLIMITED)
    override val snackbarFlow = snackbarMessages.receiveAsFlow()

    override fun showSnackBar(message: StringResource, actionLabel: StringResource?, duration: SnackbarDuration): ChannelResult<Unit> =
        snackbarMessages.trySend(
            SnackbarModel(
                message = message,
                actionLabel = actionLabel,
                duration = duration
            )
        )

}
