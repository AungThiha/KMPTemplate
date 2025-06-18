package aung.thiha.photo.album.di.core
import aung.thiha.photo.album.di.appModule
import aung.thiha.photo.album.di.overrides
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.module.Module

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
