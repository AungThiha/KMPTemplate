package aung.thiha.coroutines

import aung.thiha.coroutines.TestDispatcherHolder.testDefault
import aung.thiha.coroutines.TestDispatcherHolder.testIo
import aung.thiha.coroutines.TestDispatcherHolder.testMain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

object AppDispatchers {
    val main: CoroutineDispatcher get() = testMain ?: Dispatchers.Main
    val io: CoroutineDispatcher get() = testIo ?: Dispatchers.IO
    val default: CoroutineDispatcher get() = testDefault ?: Dispatchers.Default
}

object TestDispatcherHolder {
    var testMain: CoroutineDispatcher? = null
    var testIo: CoroutineDispatcher? = null
    var testDefault: CoroutineDispatcher? = null
}
