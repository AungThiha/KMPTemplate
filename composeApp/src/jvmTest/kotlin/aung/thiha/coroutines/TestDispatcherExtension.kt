package aung.thiha.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcherExtension(
    private val testCoroutineScheduler: TestCoroutineScheduler? = null,
    private val main: TestDispatcher? = null,
    private val io: CoroutineDispatcher? = null,
    private val default: CoroutineDispatcher? = null,
) : BeforeEachCallback, AfterEachCallback {

    override fun beforeEach(context: ExtensionContext) {
        TestDispatcherHolder.testMain = (this.main ?: UnconfinedTestDispatcher(testCoroutineScheduler ?: TestCoroutineScheduler())).also {
            Dispatchers.setMain(it)
        }
        TestDispatcherHolder.testIo = this.io ?: TestDispatcherHolder.testMain
        TestDispatcherHolder.testDefault = this.default ?: TestDispatcherHolder.testMain
    }

    override fun afterEach(context: ExtensionContext) {
        Dispatchers.resetMain()
    }
}
