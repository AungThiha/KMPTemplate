package aung.thiha.photo.album.koin

import aung.thiha.photo.album.di.appModule
import org.koin.core.context.startKoin

fun initKoin(){
    startKoin {
        modules(appModule)
    }
}