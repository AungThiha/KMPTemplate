# Testing

## Unit Tests

- Sample unit tests can be found in [`DefaultNavigationDispatcherTest.kt`](composeApp/src/jvmTest/kotlin/aung/thiha/photo/album/navigation/DefaultNavigationDispatcherTest.kt).
- Refer to the following official documentation:
    - [JUnit 5 Docs](https://junit.org/)
    - [Mokkery Docs](https://mokkery.dev/)

---

## Integration Tests

> 💡 Integration tests must live in [`composeApp/src/jvmTest`](composeApp/src/jvmTest) as they involve dependencies across multiple modules.

---

### Set Up Fakes for Integration Tests

1. **Implement a fake**  
   Take [`FakeAuthenticationDataSource.kt`](composeApp/src/jvmTest/kotlin/aung/thiha/photo/album/authentication/data/remote/service/FakeAuthenticationDataSource.kt) as a reference and implement the interface you want to fake.

2. **Create a Koin module to override the interface provision**  
   For example, in [`AuthenticationDataModuleOverride.kt`](composeApp/src/jvmTest/kotlin/aung/thiha/photo/album/di/AuthenticationDataModuleOverride.kt):

    ```kotlin
    import aung.thiha.photo.album.di.core.fake
    import org.koin.dsl.module
   
    val authenticationDataModuleOverride = module {
        fake<AuthenticationDataSource, FakeAuthenticationDataSource> {
            FakeAuthenticationDataSource()
        }
    }
    ```   
   > 💡 Use the `fake` API as shown above. This allows you to provide the interface to the production code and inject the concrete implementation to your test. With the concrete implementation, you can stub responses.

3. **Add the override module to your test overrides list**  
   Update [KoinModuleOverrides.kt](composeApp/src/jvmTest/kotlin/aung/thiha/photo/album/di/KoinModuleOverrides.kt):
   ```kotlin
    val overrides = listOf(
        sessionStorageModule,
        authenticationDataModuleOverride
    )
   ```

4. **Annotate your test with the Koin test extension and inject your fake**
   ```kotlin
    import aung.thiha.photo.album.di.core.KoinTestExtension
    import org.koin.test.KoinTest
    import org.koin.test.inject
    
    @ExtendWith(KoinTestExtension::class)
    class SplashViewModelTest : KoinTest {
    
        private val authDataSource: FakeAuthenticationDataSource by inject()
    
        @Test
        fun stubrResponse() {
            authDataSource.tokenCheckResponses += TokenCheckResponse(message = "all good")
        }
    }
    ```

5. **Enjoy!**

---

### Override Coroutine Dispatchers for Integration Tests

> ⚠️ **Do not** use `kotlinx.coroutines.Dispatchers` directly like `Dispatchers.Main`, `Dispatchers.IO`, etc.   
> Production code must always use `CoroutineDispatchers` from [`AppDispatchers`](coroutines/src/commonMain/kotlin/aung/thiha/coroutines/AppDispatchers.kt).

There are two main ways to override dispatchers during testing:

#### Option 1: Quick Setup with `UnconfinedTestDispatcher`

If you only need to use the same `UnconfinedTestDispatcher` for everything, simply annotate your test class:

```kotlin
import aung.thiha.coroutines.TestDispatcherExtension

@ExtendWith(TestDispatcherExtension::class)
class SplashViewModelTest : KoinTest {
    // Tests here will run with UnconfinedTestDispatcher
}
```
#### Option 2: Fine-Grained Dispatcher Control

Use this approach if you need:
- A custom `TestCoroutineScheduler`
- Different `TestDispatchers` for `Main`, `IO`, or `Default`
```kotlin
import aung.thiha.coroutines.TestDispatcherExtension

class SplashViewModelTest : KoinTest {

    val testScope = TestScope()

    /**
     * Option A: Override only the test scheduler
     * This automatically makes all dispatchers share the same scheduler.
     */
    @JvmField
    @RegisterExtension
    val testDispatcherExtensionOverrideTestScheduler = TestDispatcherExtension(
        testCoroutineScheduler = testScope.testScheduler
    )

    /**
     * Option B: Override specific dispatchers
     *
     * ⚠️ All TestDispatchers must share the same TestCoroutineScheduler!
     * Otherwise, virtual time won't be in sync and may lead to flaky tests
     *
     * Refer to Google’s guide on TestCoroutineScheduler and virtual time:
     * https://developer.android.com/kotlin/coroutines/test
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @JvmField
    @RegisterExtension
    val testDispatcherExtensionOverrideDispatchers = TestDispatcherExtension(
        main = StandardTestDispatcher(testScope.testScheduler),
        io = StandardTestDispatcher(testScope.testScheduler),
        default = UnconfinedTestDispatcher(testScope.testScheduler)
    )
}
```

---

### Assert Navigation in Integration Tests
Use `runNavTest` to assert navigation behavior in integration tests. This gives you:

- Access to `TestScope`, just like `runTest`
- A `SpyNavigationHandler` (`spyNav`) to assert navigation outcomes
- Declarative and readable assertions

```kotlin
import aung.thiha.photo.album.navigation.runNavTest

class SplashViewModelTest : KoinTest {
    @Test
    fun simpleNavTest() = runNavTest { spyNav ->
        spyNav shouldNavigateTo PhotoListRoute.withClearBackStack
    }

    @Test
    fun withClearBackStack() = runNavTest { spyNav ->
        spyNav shouldNavigateTo PhotoListRoute.withClearBackStack
    }

    @Test
    fun withLaunchSingleTop() = runNavTest { spyNav ->
        spyNav shouldNavigateTo PhotoListRoute.withLaunchSingleTop
    }

    @Test
    fun withPopupToNonInclusive() = runNavTest { spyNav ->
        spyNav shouldNavigateTo PhotoListRoute.withPopUpTo(SplashRoute)
    }

    @Test
    fun withPopupToInclusive() = runNavTest { spyNav ->
        spyNav shouldNavigateTo PhotoListRoute.withPopUpTo(
            popUpTo = SplashRoute,
            isInclusive = true
        )
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun useTestScope() = runNavTest { spyNav ->
        backgroundScope.launch {
            // simulate background work
        }

        val dispatcher = StandardTestDispatcher(testScheduler)
        val job = launch(dispatcher) {
            delay(100)
        }
        job.join()

        runCurrent()
        advanceTimeBy(100)
        advanceUntilIdle()
    }
}
```
