package aung.thiha.photo.album.navigation

import aung.thiha.compose.navigation.Destination
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.koin.core.component.get
import org.koin.test.KoinTest
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class SpyNavigationHandler(
    private val coroutineScope: TestScope
) : NavigationHandler {

    val navigateCalls = mutableListOf<Pair<Destination, NavigationOptions>>()
    var navigateUpCallCount = 0

    val navigateJobs = mutableListOf<Job>()

    override fun onNavigateUp(): CompletableDeferred<Boolean> {
        navigateUpCallCount++
        return CompletableDeferred(true) // Always return success
    }

    override fun onNavigation(destination: Destination, navigationOptions: NavigationOptions): Job {
        navigateCalls += destination to navigationOptions
        return coroutineScope.launch {

        }.also {
            navigateJobs += it
        }
    }
}

infix fun SpyNavigationHandler.shouldNavigateTo(destination: Destination) {
    assertTrue(
        navigateCalls.any { (d, _) -> d == destination },
        "Expected navigation to $destination but got: ${navigateCalls.map { it.first }}"
    )
}

infix fun SpyNavigationHandler.shouldNavigateTo(expected: DestinationWithOptions) {
    val match = navigateCalls.any { (actualDest, actualOpts) ->
        actualDest == expected.destination &&
                actualOpts.launchSingleTop == expected.launchSingleTop &&
                actualOpts.backStackOptions == expected.backStackOptions
    }

    assert(match) {
        "Expected navigation to ${expected.destination} with $expected, but got:\n" +
                navigateCalls.joinToString("\n") { (dest, opt) -> "$dest with $opt" }
    }
}

fun SpyNavigationHandler.shouldNavigateUp(times: Int = 1) {
    assertEquals(times, navigateUpCallCount, "Expected navigateUp() to be called $times times")
}

fun KoinTest.runNavTest(
    context: CoroutineContext = EmptyCoroutineContext,
    timeout: Duration = 60.seconds,
    testBody: suspend TestScope.(SpyNavigationHandler) -> Unit
): TestResult {
    val testScope = TestScope(context)
    val spyNavigationHandler = SpyNavigationHandler(testScope)
    get<NavigationDispatcher>().setHandler(spyNavigationHandler)
    return testScope.runTest(timeout) {
        testBody(spyNavigationHandler)
    }
}
