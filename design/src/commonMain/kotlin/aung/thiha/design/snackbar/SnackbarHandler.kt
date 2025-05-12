package aung.thiha.design.snackbar

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.getString

@Composable
fun SnackbarHandler(
    snackbarHostState: SnackbarHostState,
    snackbarFlow: Flow<SnackbarChannel.SnackbarMessage>
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        snackbarFlow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collect { message ->
                when (message) {
                    is SnackbarChannel.SnackbarMessage.ResourceMessage -> {
                        getString(message.value)
                    }
                    is SnackbarChannel.SnackbarMessage.StringMessage -> {
                        message.value
                    }
                }.let {
                    snackbarHostState.showSnackbar(message = it)
                }
            }
    }
}