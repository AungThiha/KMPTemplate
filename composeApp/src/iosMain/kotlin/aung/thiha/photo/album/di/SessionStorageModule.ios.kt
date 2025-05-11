package aung.thiha.photo.album.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import aung.thiha.storage.createKeyValueStorage
import org.koin.core.module.Module
import org.koin.dsl.module

actual val sessionStorageModule: Module
    get() = module {
        single<DataStore<Preferences>>(NAMED_AUTHENTICATION_PREFERENCE) {
            createKeyValueStorage(PREF_AUTHENTICATION)
        }
    }