package aung.thiha.photo.album.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import aung.thiha.photo.album.storage.createDataStore
import aung.thiha.photo.album.storage.NAMED_AUTHENTICATION_PREFERENCE
import org.koin.core.module.Module
import org.koin.dsl.module

actual val dataStorageModule: Module
    get() = module {
        single<DataStore<Preferences>>(NAMED_AUTHENTICATION_PREFERENCE) {
            createDataStore()
        }
    }