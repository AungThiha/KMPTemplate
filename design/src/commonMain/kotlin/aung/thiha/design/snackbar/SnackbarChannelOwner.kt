package aung.thiha.design.snackbar

import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.StringResource

interface SnackbarChannelOwner {
    val snackbarFlow: Flow<SnackbarChannel.SnackbarMessage>
    /**
     * If the buffer is full, showing Snackbar will fail
     * If it fails, it's recommended to make sure your UI logic doesn't emit too many snackbars
     * After all, buffer size is 64. It's impossible to have 64 snackbars in a short period time
     * unless there's a serious error
    * */
    fun showSnackBar(message: String): ChannelResult<Unit>
    /**
     * If the buffer is full, showing Snackbar will fail
     * If it fails, it's recommended to make sure your UI logic doesn't emit too many snackbars
     * After all, buffer size is 64. It's impossible to have 64 snackbars in a short period time
     * unless there's a serious error
     * */
    fun showSnackBar(message: StringResource): ChannelResult<Unit>
}