package aung.thiha.snackbar

import androidx.compose.material.SnackbarDuration
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.StringResource

interface SnackbarChannelOwner {
    val snackbarFlow: Flow<SnackbarModel>
    /**
     * If you're using this [SnackbarChannel] and if you emit too many SnackbarModel, it can lead to OutOfMemoryError
     * That said, in real-world applications, it's impractical to have more than a few Snackbars.
    * */
    fun showSnackBar(message: String, actionLabel: String? = null, duration: SnackbarDuration = SnackbarDuration.Short): ChannelResult<Unit>
    /**
     * If you're using this [SnackbarChannel] and if you emit too many SnackbarModel, it can lead to OutOfMemoryError
     * That said, in real-world applications, it's impractical to have more than a few Snackbars.
     * */
    fun showSnackBar(message: StringResource, actionLabel: String? = null, duration: SnackbarDuration = SnackbarDuration.Short): ChannelResult<Unit>
}