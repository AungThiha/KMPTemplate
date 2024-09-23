package aung.thiha.photo.album.di

import android.content.Context
import aung.thiha.photo.album.provider.ContextHolder
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<Context> {
        ContextHolder.context
    }
}
