package aung.thiha.photo.album.di.core
import aung.thiha.photo.album.di.appModule
import aung.thiha.photo.album.di.overrides
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.module.Module

/**
 * @param extraOverrides - if the override is applicable only to a particular test class,
 * use this param in that particular test class. This leaves the overrides unchanged for other test classes.
 * */
class KoinTestExtension(
    private val extraOverrides: List<Module> = emptyList()
) : BeforeEachCallback, AfterEachCallback {
    override fun beforeEach(context: ExtensionContext) {
        startKoin {
            modules(
                appModule + overrides + extraOverrides
            )
        }
    }

    override fun afterEach(context: ExtensionContext) {
        stopKoin()
    }
}
