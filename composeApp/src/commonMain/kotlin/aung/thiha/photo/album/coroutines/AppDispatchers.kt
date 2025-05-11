package aung.thiha.photo.album.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

object AppDispatchers {

    private var testMain: CoroutineDispatcher? = null
    private var testIo: CoroutineDispatcher? = null
    private var testDefault: CoroutineDispatcher? = null

    val main: CoroutineDispatcher get() = testMain ?: Dispatchers.Main
    val io: CoroutineDispatcher get() = testIo ?: Dispatchers.IO
    val default: CoroutineDispatcher get() = testDefault ?: Dispatchers.Default
}
