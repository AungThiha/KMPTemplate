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

/**
 * This class offers two mutually exclusive ways to control dispatcher behavior:
 *
 * 1. Use the primary constructor to manually provide one or all of [main], [io] and [default] dispatchers.
 *
 * 2. Use the secondary constructor with a shared [TestCoroutineScheduler]
 *   -> Automatically sets [main], [io] and [default] dispatchers using that scheduler.
 *
 * The intent is to prevent developers from mixing both approaches.
 * For example, passing a shared [TestCoroutineScheduler] while also overriding individual dispatchers \
 * could lead to inconsistent virtual time due to different schedulers being used.
 *
 * This restriction is **enforced** at the API level by not exposing a constructor that takes both.
 *
 * However, the primary constructor is still public,
 * so developers can manually pass in dispatchers that use **different** [TestCoroutineScheduler] instances.
 * To guard against this, [beforeEach] performs a **runtime validation**,
 * which throws an [IllegalStateException] if dispatchers use different schedulers.
 *
 * For more information why all TestDispatchers must share the same TestCoroutineScheduler, see:
 * https://developer.android.com/kotlin/coroutines/test
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcherExtension(
    private val main: TestDispatcher = UnconfinedTestDispatcher(),
    private val io: TestDispatcher = main,
    private val default: TestDispatcher = main,
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
         * Quotes from Google:
         *
         * In local unit tests, the Main dispatcher that wraps the Android UI thread will be unavailable,
         * as these tests are executed on a local JVM and not an Android device
         *
         * some APIs such as viewModelScope use a hardcoded Main dispatcher under the hood.
         *
         * To replace the Main dispatcher with a TestDispatcher in all cases,
         * use the Dispatchers.setMain and Dispatchers.resetMain functions
         *
         * If the Main dispatcher has been replaced with a TestDispatcher,
         * any newly-created TestDispatchers will automatically use the scheduler from the Main dispatcher,
         * including the StandardTestDispatcher created by runTest if no other dispatcher is passed to it.
         *
         * Reference: https://developer.android.com/kotlin/coroutines/test#setting-main-dispatcher
         * =================================================================
         * runNavTest from this project is written with this design in mind.
         * StandardTestDispatcher created by runNavTest will also automatically use the scheduler \
         * from the Main dispatcher
        * */
        Dispatchers.setMain(main)
    }

    override fun afterEach(context: ExtensionContext) {
        Dispatchers.resetMain()
    }
}
