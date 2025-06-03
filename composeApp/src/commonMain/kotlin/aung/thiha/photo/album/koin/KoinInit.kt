package aung.thiha.photo.album.koin

import aung.thiha.photo.album.di.appModule
import org.koin.core.context.startKoin

/**
 * WARNING: This is used in [iOSApp.swift]
 * Don't delete this
* */
fun initKoin(){
    startKoin {
        modules(appModule)
    }
}