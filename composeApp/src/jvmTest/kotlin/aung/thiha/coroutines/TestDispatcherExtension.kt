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
    private val main: TestDispatcher = UnconfinedTestDispatcher(),
    private val io: CoroutineDispatcher = main,
    private val default: CoroutineDispatcher = main,
) : BeforeEachCallback, AfterEachCallback {

    constructor(
        testCoroutineScheduler: TestCoroutineScheduler
    ) : this(
        main = UnconfinedTestDispatcher(testCoroutineScheduler),
        io = UnconfinedTestDispatcher(testCoroutineScheduler),
        default = UnconfinedTestDispatcher(testCoroutineScheduler)
    )

    override fun beforeEach(context: ExtensionContext) {

        val schedulers = listOf(main, io, default)
            .filterIsInstance<TestDispatcher>()
            .map { it.scheduler }
            .distinct()

        if (schedulers.size > 1) {
            throw IllegalStateException(
                "All TestDispatchers must share the same TestCoroutineScheduler. " +
                        "Otherwise, virtual time won't be in sync.\n" +
                        "Refer to Google’s guide on TestCoroutineScheduler and virtual time:\n" +
                        "https://developer.android.com/kotlin/coroutines/test"
            )
        }

        TestDispatcherHolder.testMain = main
        TestDispatcherHolder.testIo = io
        TestDispatcherHolder.testDefault = default

        /**
         * Quote from Google:
         * If the Main dispatcher has been replaced with a TestDispatcher,
         * any newly-created TestDispatchers will automatically use the scheduler from the Main dispatcher,
         * including the StandardTestDispatcher created by runTest if no other dispatcher is passed to it.
         *
         * Reference: https://developer.android.com/kotlin/coroutines/test#setting-main-dispatcher
         *
         * This also applies to runNavTest from this project.
         * StandardTestDispatcher created by runNavTest will automatically use the scheduler from the Main dispatcher
        * */
        Dispatchers.setMain(main)
    }

    override fun afterEach(context: ExtensionContext) {
        Dispatchers.resetMain()
    }
}
