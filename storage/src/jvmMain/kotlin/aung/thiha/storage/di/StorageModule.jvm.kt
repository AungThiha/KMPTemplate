package aung.thiha.storage.di

import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * junit5 works only in JVM
 * jvmTest folder cannot be created using jvm target setup in Gradle
 * but then, with jvm target setup, we need to provide `actual` for JVM
 * This is a trade-off
* */
actual val storageModule: Module = module {

}