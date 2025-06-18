package aung.thiha.photo.album.di
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin

class KoinTestExtension : BeforeEachCallback, AfterEachCallback {
    override fun beforeEach(context: ExtensionContext) {
        startKoin {
            modules(appModule)
        }
    }
    
    override fun afterEach(context: ExtensionContext) {
        stopKoin()
    }
}
