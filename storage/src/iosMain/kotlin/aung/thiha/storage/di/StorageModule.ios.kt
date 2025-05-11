package aung.thiha.storage.di

import aung.thiha.storage.CreateKeyValueStorage
import aung.thiha.storage.createPrefDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual val storageModule: Module
    get() = module {
        single<CreateKeyValueStorage> {
            CreateKeyValueStorage { storageScope -> createPrefDataStore(storageScope) }
        }
    }